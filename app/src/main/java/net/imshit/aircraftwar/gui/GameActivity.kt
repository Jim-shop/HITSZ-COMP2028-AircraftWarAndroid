package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import net.imshit.aircraftwar.Difficulty
import net.imshit.aircraftwar.databinding.ActivityGameBinding
import net.imshit.aircraftwar.logic.AbstractGame
import net.imshit.aircraftwar.logic.EasyGame
import net.imshit.aircraftwar.logic.HardGame
import net.imshit.aircraftwar.logic.MediumGame

class GameActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context, gameMode: Difficulty, soundMode: Boolean) {
            context.startActivity(Intent(context, GameActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("soundMode", soundMode)
            })
        }
    }

    var width: Int = 0
        private set
    var height: Int = 0
        private set

    val refreshInterval = 10

    var gameMode = Difficulty.EASY
        private set
    var soundMode = true
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取配置
        with(windowManager.currentWindowMetrics.bounds) {
            width = right - left
            height = bottom - top
        }
        intent.apply {
            gameMode = getSerializableExtra("gameMode", Difficulty::class.java) ?: Difficulty.EASY
            soundMode = getBooleanExtra("soundMode", true)
        }
        val game: AbstractGame = when (gameMode) {
            Difficulty.EASY -> EasyGame(this)
            Difficulty.MEDIUM -> MediumGame(this)
            Difficulty.HARD -> HardGame(this)
        }
        with(ActivityGameBinding.inflate(layoutInflater)) {
            setContentView(root.apply {
                addView(game, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            })
        }
        // 隐藏任务栏、导航栏
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
    }
}