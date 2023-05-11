package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewTreeObserver
import net.imshit.aircraftwar.element.aircraft.enemy.BossEnemy
import net.imshit.aircraftwar.element.aircraft.enemy.Enemies
import net.imshit.aircraftwar.element.aircraft.hero.HeroAircraft
import net.imshit.aircraftwar.element.animation.DyingAnimation
import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.Difficulty
import net.imshit.aircraftwar.logic.GameEvents
import net.imshit.aircraftwar.logic.ImageManager

sealed class Games(context: Context, attrs: AttributeSet?, soundMode: Boolean) :
    SurfaceView(context, attrs), SurfaceHolder.Callback, Runnable {
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
    val images = ImageManager(context.applicationContext)

    // configs
    protected abstract val background: Bitmap
    val refreshInterval = 10

    // strategies


    // status
    var score = 0
    var time = 0
    var isGameOver = false

    // variables
    lateinit var heroAircraft: HeroAircraft
    val enemyAircrafts = mutableListOf<Enemies>()
    val heroBullets = mutableListOf<HeroBullet>()
    val enemyBullets = mutableListOf<EnemyBullet>()
    val props = mutableListOf<Props>()
    val animations = mutableListOf<DyingAnimation>()
    var boss: BossEnemy? = null

    // bundle of variables
    val elementLists = listOf(enemyAircrafts, heroBullets, enemyBullets, props, animations)
    val enemyListenerLists = listOf(enemyAircrafts, enemyBullets)

    init {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
//                TODO("width, height ready")
            }
        })
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.e("fuck", width.toString())
        Log.e("fuck", height.toString())
//        TODO("Not yet implemented")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.e("FFFFF", width.toString())
//        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        this.isGameOver = true
    }

    override fun run() {
        init()
        while (!this.isGameOver) {
            synchronized(holder) {
                update()
                draw()
            }
        }
    }

    fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> {
                TODO()
            }
        }
    }

    fun initHeroController() {
        setOnTouchListener { view, motionEvent ->
            view.performClick()
            this.heroAircraft.x = motionEvent.x
            this.heroAircraft.y = motionEvent.y
            true
        }
    }

    fun init() {
        this.heroAircraft = HeroAircraft(game = this, maxHp = 1000, power = 30, shootNum = 1)
    }

    fun update() {
        this.time += this.refreshInterval

    }

    fun draw() {

    }
}