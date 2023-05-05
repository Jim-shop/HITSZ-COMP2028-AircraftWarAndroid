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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Param.WINDOW_WIDTH = TODO()
    }
}