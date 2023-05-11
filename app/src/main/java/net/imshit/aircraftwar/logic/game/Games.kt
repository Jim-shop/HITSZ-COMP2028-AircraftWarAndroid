package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewTreeObserver
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.aircraft.enemy.BossEnemy
import net.imshit.aircraftwar.element.aircraft.enemy.Enemies
import net.imshit.aircraftwar.element.aircraft.hero.HeroAircraft
import net.imshit.aircraftwar.element.animation.DyingAnimation
import net.imshit.aircraftwar.element.bullet.Bullets
import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.Difficulty
import net.imshit.aircraftwar.logic.EnemyListener
import net.imshit.aircraftwar.logic.GameEvents
import net.imshit.aircraftwar.logic.ImageManager
import net.imshit.aircraftwar.logic.generate.enemy.EnemyGenerateStrategies

sealed class Games(context: Context, attrs: AttributeSet?, soundMode: Boolean) :
    SurfaceView(context, attrs), SurfaceHolder.Callback {
    companion object {
        fun getGames(context: Context, gameMode: Difficulty, soundMode: Boolean): Games {
            return when (gameMode) {
                Difficulty.EASY -> EasyGame(context, soundMode)
                Difficulty.MEDIUM -> MediumGame(context, soundMode)
                Difficulty.HARD -> HardGame(context, soundMode)
            }
        }
    }

    /** used by design tool */
    constructor(context: Context, attrs: AttributeSet?) : this(
        context = context, attrs = attrs, soundMode = false
    )

    // utils
    lateinit var images: ImageManager
    private val drawingThread = Thread {
        while (!this.isStopped) {
            update()
            draw()
            Thread.sleep(this.refreshInterval.toLong())
        }
    }

    // configs
    lateinit var background: Bitmap
    private val refreshInterval = 10

    // strategies
    abstract val generateStrategy: EnemyGenerateStrategies

    // status
    var score = 0
    var time = 0
    var isGameOver = false
    private var isStopped = false

    // variables
    lateinit var heroAircraft: HeroAircraft
    val enemyAircrafts = mutableListOf<Enemies>()
    val heroBullets = mutableListOf<HeroBullet>()
    val enemyBullets = mutableListOf<EnemyBullet>()
    val props = mutableListOf<Props>()
    val animations = mutableListOf<DyingAnimation>()
    var boss: BossEnemy? = null

    // bundle of variables
    private val autoElementLists =
        listOf(animations, props, enemyBullets, heroBullets, enemyAircrafts)
    private val enemyListenerLists = listOf<List<EnemyListener>>(enemyAircrafts, enemyBullets)
    private val heroList = mutableListOf<HeroAircraft>()
    private val drawingElementLists =
        listOf(heroList, animations, props, enemyBullets, heroBullets, enemyAircrafts)
    private val lifebarElementLists = listOf(heroList, enemyAircrafts)

    init {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                init()
            }
        })
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        this.drawingThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        this.isStopped = true
        this.drawingThread.interrupt()
    }

    fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> {
                // TODO: 音乐
                enemyListenerLists.flatten().forEach { item -> item.notify(e) }
            }
        }
    }

    private fun initHeroController() {
        setOnTouchListener { view, motionEvent ->
            view.performClick()
            this.heroAircraft.x = motionEvent.x
            this.heroAircraft.y = motionEvent.y
            true
        }
    }

    open fun init() {
        this.images = ImageManager(context.applicationContext, width)
        this.heroAircraft = HeroAircraft(game = this, maxHp = 1000, power = 30, shootNum = 1)
        this.heroList.add(this.heroAircraft)
        this.holder.addCallback(this@Games)
        initHeroController()
        // TODO: 音乐
    }

    private fun update() {
        this.time += this.refreshInterval
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
            // TODO: 音乐
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
            // TODO: 音乐
        }
        if (this.generateStrategy.isTimeForHeroShoot()) {
            this.heroBullets.addAll(heroAircraft.shoot())
            // TODO: 音乐
        }
    }

    private fun moveAction() {
        this.autoElementLists.forEach { list -> list.forEach { it.forward(this.refreshInterval) } }
    }

    private fun crashCheck() {
        // 敌机子弹攻击英雄
        this.enemyBullets.filter(this.heroAircraft::crash).forEach { bullet ->
            this.heroAircraft.decreaseHp(bullet.power)
            bullet.vanish()
            // TODO: 音乐
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
                    // TODO: 音乐
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
            // todo: 音乐
        }
    }

    private fun cleanInvalid() {
        this.autoElementLists.forEach { list -> list.removeIf(AbstractFlyingObject::notValid) }
        this.boss?.let {
            if (it.notValid()) {
                this.boss = null
                // TODO: 音乐
            }
        }
    }

    private fun gameOver() {
        this.isGameOver = true
        this.isStopped = true
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
        val BAR_OFFSET = 15f
        val BAR_TEXT_OFFSET = 15f
        val BAR_LENGTH = 50f
        val BAR_HEIGHT = 10f
        this.lifebarElementLists.flatten().forEach {
            val hp = it.hp
            val maxHp = it.maxHp
            val barStartX = it.x - BAR_LENGTH / 2
            val barEndX = barStartX + BAR_LENGTH
            val barCurrX = barStartX + BAR_LENGTH * hp / maxHp
            val barTopY = it.y - it.height / 2 - BAR_OFFSET
            val barButtomY = barTopY + BAR_HEIGHT
            this.paint.color = Color.BLACK
            this.paint.style = Paint.Style.STROKE
            canvas.drawRect(barStartX, barTopY, barEndX, barButtomY, this.paint)
            this.paint.color = Color.WHITE
            this.paint.style = Paint.Style.FILL
            canvas.drawRect(barStartX, barTopY, barEndX, barButtomY, this.paint)
            this.paint.color = Color.RED
            canvas.drawRect(barStartX, barTopY, barCurrX, barButtomY, this.paint)
            this.paint.textSize = 20f
            canvas.drawText("$hp/$maxHp", barStartX, barTopY - BAR_TEXT_OFFSET, this.paint)
        }
    }

    private fun paintScore(canvas: Canvas) {
        val SCORE_X = 10f
        val SCORE_Y = 25f
        this.paint.color = Color.YELLOW
        this.paint.textSize = 50f
        canvas.drawText(
            context.getString(R.string.game_canvas_text_score, this.score),
            SCORE_X,
            SCORE_Y,
            this.paint
        )
    }

    private fun draw() {
        val canvas = this.holder.lockCanvas() ?: return
        // 循环绘制背景
        paintBackground(canvas)
        // 绘制物件
        paintObject(canvas)
        // 绘制血条
        paintLife(canvas)
        // 绘制得分
        paintScore(canvas)
        this.holder.unlockCanvasAndPost(canvas)
    }
}

