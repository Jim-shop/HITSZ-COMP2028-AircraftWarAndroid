package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.Difficulty
import net.imshit.aircraftwar.databinding.ActivityGameBinding

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
    var soundMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(windowManager.currentWindowMetrics.bounds) {
            width = right - left
            height = bottom - top
        }
        with(ActivityGameBinding.inflate(layoutInflater)) {
            setContentView(root)
            // handle input string
            intent.extras?.apply {
                gameMode = getSerializable("gameMode", Difficulty::class.java) ?: Difficulty.EASY
                soundMode = getBoolean("soundMode", true)
            }
        }
    }
}