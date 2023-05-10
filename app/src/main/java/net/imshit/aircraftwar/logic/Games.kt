package net.imshit.aircraftwar.logic

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewTreeObserver
import net.imshit.aircraftwar.element.aircraft.hero.HeroAircraft

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

    // used by design tool
    constructor(context: Context, attrs: AttributeSet?) : this(
        context = context, attrs = attrs, soundMode = false
    )

    val images = ImageManager(context.applicationContext)
    protected abstract val background: Bitmap

    val refreshInterval = 10
    var screenWidth = 0
        private set
    var screenHeight = 0
        private set

    lateinit var heroAircraft: HeroAircraft

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
        Log.e("fuck", width.toString())
        Log.e("fuck", height.toString())
//        TODO("Not yet implemented")
    }

    override fun run() {
        Log.e("FFFFF", "RUN")
    }

    fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> {
                TODO()
            }
        }
    }
}