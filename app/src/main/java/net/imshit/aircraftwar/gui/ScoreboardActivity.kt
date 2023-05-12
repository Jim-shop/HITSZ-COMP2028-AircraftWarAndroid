package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.databinding.ActivityScoreboardBinding
import net.imshit.aircraftwar.logic.Difficulty

class ScoreboardActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context, gameMode: Difficulty) {
            context.startActivity(Intent(context, ScoreboardActivity::class.java).apply {
                putExtra("gameMode", gameMode)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityScoreboardBinding.inflate(layoutInflater)) {
            setContentView(root)
        }
    }
}