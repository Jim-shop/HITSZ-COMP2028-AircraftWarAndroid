package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewTreeObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.fighting.CommunicateInfo
import net.imshit.aircraftwar.data.fighting.FightingClient
import net.imshit.aircraftwar.data.resource.ImageManager
import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.aircraft.enemy.BossEnemy
import net.imshit.aircraftwar.element.aircraft.enemy.Enemies
import net.imshit.aircraftwar.element.aircraft.hero.HeroAircraft
import net.imshit.aircraftwar.element.aircraft.hero.HeroController
import net.imshit.aircraftwar.element.animation.DyingAnimation
import net.imshit.aircraftwar.element.bullet.Bullets
import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.event.EnemyListener
import net.imshit.aircraftwar.element.event.GameEvents
import net.imshit.aircraftwar.element.generate.enemy.EnemyGenerateStrategies
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.music.BasicMusicStrategy
import net.imshit.aircraftwar.logic.music.BgmType
import net.imshit.aircraftwar.logic.music.MusicStrategies
import net.imshit.aircraftwar.logic.music.MuteMusicStrategy

sealed class Games(
    context: Context,
    attrs: AttributeSet?,
    soundMode: Boolean,
    private val onlineMode: Boolean,
    roomId: Int,
    private val handler: Handler?
) : SurfaceView(context, attrs), SurfaceHolder.Callback, CoroutineScope by CoroutineScope(
    Dispatchers.Default
) {
    companion object {
        fun getGames(
            context: Context,
            gameMode: Difficulty,
            soundMode: Boolean,
            onlineMode: Boolean,
            roomId: Int,
            handler: Handler
        ): Games {
            return when (gameMode) {
                Difficulty.EASY -> EasyGame(context, null, soundMode, onlineMode, roomId, handler)
                Difficulty.MEDIUM -> MediumGame(
                    context, null, soundMode, onlineMode, roomId, handler
                )

                Difficulty.HARD -> HardGame(context, null, soundMode, onlineMode, roomId, handler)
            }
        }

        const val REFRESH_INTERVAL = 15

        private const val BAR_OFFSET = 15f
        private const val BAR_TEXT_OFFSET = 15f
        private const val BAR_LENGTH = 50f
        private const val BAR_HEIGHT = 10f

        private const val SCORE_SIZE = 128f
        private const val SCORE_X = 10f
        private const val SCORE_Y = SCORE_SIZE + 10f

        private const val REMOTE_SIZE = 64f
        private const val REMOTE_X = SCORE_X
        private const val REMOTE_Y = SCORE_Y + REMOTE_SIZE + 10f
    }

    // utils
    private val heroController = HeroController(context)
    lateinit var images: ImageManager
    private var logicJob: Job? = null
    private var client = if (onlineMode) FightingClient(context, roomId) else null

    // configs
    lateinit var background: Bitmap

    // strategies
    abstract val generateStrategy: EnemyGenerateStrategies
    private val musicStrategy: MusicStrategies =
        if (soundMode) BasicMusicStrategy(context) else MuteMusicStrategy

    // status
    private var score = 0
    private var time = 0
    private var isGameOver = false
    private var isStopped = true
    private var remoteInfo: CommunicateInfo? = CommunicateInfo(0, 0, 0)

    // variables
    lateinit var heroAircraft: HeroAircraft
    private val enemyAircrafts = mutableListOf<Enemies>()
    private val heroBullets = mutableListOf<HeroBullet>()
    private val enemyBullets = mutableListOf<EnemyBullet>()
    private val props = mutableListOf<Props>()
    private val animations = mutableListOf<DyingAnimation>()
    private var boss: BossEnemy? = null

    // bundle of variables
    private val heroList = mutableListOf<HeroAircraft>()
    private val autoElementLists =
        listOf(animations, props, enemyBullets, heroBullets, enemyAircrafts, heroList)
    private val enemyListenerLists = listOf<List<EnemyListener>>(enemyAircrafts, enemyBullets)
    private val drawingElementLists =
        listOf(enemyBullets, heroBullets, enemyAircrafts, props, animations, heroList)
    private val lifeBarElementLists = listOf(heroList, enemyAircrafts)

    init {
        client?.run()
        client?.onData = {
            this.remoteInfo = it
        }
        client?.onQuit = {
            this.remoteInfo = null
        }
        // 等布局完成就开始获取图片等初始化工作
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                init()
            }
        })
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        resume()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stop()
    }

    fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> {
                this.musicStrategy.playExplode()
                this.enemyListenerLists.flatten().forEach { item -> item.notify(e) }
                this.enemyAircrafts.filter(AbstractFlyingObject::notValid).forEach { aircraft ->
                    this.animations.add(aircraft.animation)
                    this.score += aircraft.credits
                }
            }
        }
    }

    open fun init() {
        this.images = ImageManager(context.applicationContext, width)
        this.heroAircraft = HeroAircraft(
            game = this, maxHp = 1000, power = 50, shootNum = 1, controller = this.heroController
        )
        this.heroList.add(this.heroAircraft)
        this.holder.addCallback(this)
        this.heroController.init(this)
        this.musicStrategy.setBgm(BgmType.NORMAL)
    }

    private fun resume() {
        if (!isGameOver) {
            this.isStopped = false
            this.musicStrategy.setBgm(BgmType.NORMAL)
            this.heroController.start()
            this.logicJob?.cancel()
            this.logicJob = launch {
                while (!isStopped) {
                    runBlocking {
                        launch { update() }
                        launch { draw() }
                        delay(REFRESH_INTERVAL.toLong())
                    }
                }
            }
        }
    }

    private fun stop() {
        this.isStopped = true
        this.musicStrategy.setBgm(BgmType.NONE)
        this.heroController.stop()
        this.logicJob?.cancel()
        this.logicJob = null
    }

    private fun update() {
        this.time += REFRESH_INTERVAL
        this.generateStrategy.inform(this.time, this.score)
        // 产生新敌机
        this.generateEnemy()
        // 发射子弹
        this.shootAction()
        // 飞行物移动
        this.moveAction()
        // 撞击检测
        this.crashCheck()
        // 清理已损毁的飞行物
        this.cleanInvalid()
        // 发送得分
        this.sendRemote()
        // 检查游戏结束
        if (this.heroAircraft.hp <= 0) {
            this.gameOver()
        }
    }

    private fun generateEnemy() {
        // BOSS 机生成
        if (this.generateStrategy.isTimeToGenerateBoss(this.boss)) {
            this.generateStrategy.generateBoss()!!.let {
                this.boss = it
                this.enemyAircrafts.add(it)
            }
            this.musicStrategy.setBgm(BgmType.BOSS)
        }
        // 敌机生成
        if (this.generateStrategy.isTimeToGenerateEnemy()) {
            this.enemyAircrafts.addAll(this.generateStrategy.generateEnemy(enemyAircrafts.size))
        }
    }

    private fun shootAction() {
        // 敌机射出子弹
        if (this.generateStrategy.isTimeForEnemyShoot()) {
            this.enemyAircrafts.forEach { enemy -> this.enemyBullets.addAll(enemy.shoot()) }
            this.musicStrategy.playBulletShoot()
        }
        if (this.generateStrategy.isTimeForHeroShoot()) {
            this.heroBullets.addAll(heroAircraft.shoot())
            this.musicStrategy.playBulletShoot()
        }
    }

    private fun moveAction() {
        this.autoElementLists.forEach { list -> list.forEach { it.forward(REFRESH_INTERVAL) } }
    }

    private fun crashCheck() {
        // 敌机子弹攻击英雄
        this.enemyBullets.filter(this.heroAircraft::crash).forEach { bullet ->
            this.heroAircraft.decreaseHp(bullet.power)
            bullet.vanish()
            this.musicStrategy.playBulletHit()
        }
        // 英雄子弹攻击敌机
        this.heroBullets.filter(Bullets::isValid).forEach { bullet ->
            this.enemyAircrafts.filter(Enemies::isValid).filter(bullet::crash)
                .forEach { enemyAircraft ->
                    enemyAircraft.decreaseHp(bullet.power)
                    if (enemyAircraft.notValid()) {
                        this.props.addAll(enemyAircraft.prop())
                        this.animations.add(enemyAircraft.animation)
                        this.score += enemyAircraft.credits
                    }
                    bullet.vanish()
                    this.musicStrategy.playBulletHit()
                }
        }
        // 敌机与英雄机相撞，均损毁
        this.enemyAircrafts.filter(Enemies::isValid).filter(this.heroAircraft::crash)
            .forEach { enemyAircraft ->
                enemyAircraft.vanish()
                this.heroAircraft.decreaseHp(Integer.MAX_VALUE)
            }
        // 我方获得道具，道具生效
        this.props.filter(this.heroAircraft::crash).forEach { prop ->
            prop.activate()
            prop.vanish()
            this.musicStrategy.playSupplyGet()
        }
    }

    private fun cleanInvalid() {
        this.autoElementLists.forEach { list -> list.removeIf(AbstractFlyingObject::notValid) }
        this.boss?.let {
            if (it.notValid()) {
                this.boss = null
                this.musicStrategy.setBgm(BgmType.NORMAL)
            }
        }
    }

    private fun sendRemote() {
        client?.send(CommunicateInfo(0, score, heroAircraft.hp))
    }

    private fun gameOver() {
        stop()
        this.isGameOver = true
        this.musicStrategy.playGameOver()
        this.handler?.sendMessage(Message.obtain().apply {
            what = score
        })
    }

    private var backgroundTop = 0
    private val paint = Paint()

    private fun paintBackground(canvas: Canvas) {
        for (top in (this.backgroundTop - this.background.height) until (this.height) step (this.background.height)) {
            canvas.drawBitmap(this.background, 0f, top.toFloat(), this.paint)
        }
        this.backgroundTop += 1
        if (this.backgroundTop >= this.background.height) {
            this.backgroundTop = 0
        }
    }

    private fun paintObject(canvas: Canvas) {
        this.drawingElementLists.flatten().forEach {
            val image = it.image
            val leftUpX = it.x - image.width / 2f
            val leftUpY = it.y - image.height / 2f
            canvas.drawBitmap(image, leftUpX, leftUpY, this.paint)
        }
    }

    private fun paintLife(canvas: Canvas) {
        this.lifeBarElementLists.flatten().forEach {
            val hp = it.hp
            val maxHp = it.maxHp
            val barStartX = it.x - BAR_LENGTH / 2
            val barEndX = barStartX + BAR_LENGTH
            val barCurrX = barStartX + BAR_LENGTH * hp / maxHp
            val barTopY = it.y - it.height / 2 - BAR_OFFSET
            val barBottomY = barTopY + BAR_HEIGHT
            this.paint.color = Color.BLACK
            this.paint.style = Paint.Style.STROKE
            canvas.drawRect(barStartX, barTopY, barEndX, barBottomY, this.paint)
            this.paint.color = Color.WHITE
            this.paint.style = Paint.Style.FILL
            canvas.drawRect(barStartX, barTopY, barEndX, barBottomY, this.paint)
            this.paint.color = Color.RED
            canvas.drawRect(barStartX, barTopY, barCurrX, barBottomY, this.paint)
            this.paint.textSize = 20f
            this.paint.typeface = Typeface.DEFAULT
            canvas.drawText("$hp/$maxHp", barStartX, barTopY - BAR_TEXT_OFFSET, this.paint)
        }
    }

    private fun paintScore(canvas: Canvas) {
        this.paint.color = Color.YELLOW
        this.paint.textSize = SCORE_SIZE
        this.paint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText(
            context.getString(R.string.game_canvas_text_score, this.score),
            SCORE_X,
            SCORE_Y,
            this.paint
        )
    }

    private fun paintRemote(canvas: Canvas) {
        if (onlineMode) {
            this.paint.color = Color.WHITE
            this.paint.textSize = REMOTE_SIZE
            this.paint.typeface = Typeface.DEFAULT
            if (remoteInfo != null) {
                canvas.drawText(
                    context.getString(
                        R.string.game_canvas_text_other_info,
                        remoteInfo?.score,
                        remoteInfo?.life
                    ),
                    REMOTE_X,
                    REMOTE_Y,
                    this.paint
                )
            } else {
                canvas.drawText(
                    context.getString(R.string.game_canvas_text_other_offline),
                    REMOTE_X,
                    REMOTE_Y,
                    this.paint
                )
            }

        }
    }

    private fun draw() {
        val canvas = this.holder.lockCanvas() ?: return
        // 循环绘制背景
        paintBackground(canvas)
        // 绘制得分
        paintScore(canvas)
        // 绘制远程得分
        paintRemote(canvas)
        // 绘制物件
        paintObject(canvas)
        // 绘制血条
        paintLife(canvas)
        if (this.holder.surface.isValid) {
            this.holder.unlockCanvasAndPost(canvas)
        }
    }
}

