package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.app.AppInfoDialog
import net.imshit.aircraftwar.data.scoreboard.ScoreboardDaoSharedPreferences
import net.imshit.aircraftwar.databinding.ActivityManageSpaceBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class ManageSpaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityManageSpaceBinding.inflate(layoutInflater)) {
            setContentView(root)

            amsTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            amsBtnClear.setOnClickListener {
                it as MaterialButton
                it.isEnabled = false
                listOf(
                    Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD
                ).forEach { gameMode ->
                    ScoreboardDaoSharedPreferences(this@ManageSpaceActivity, gameMode).apply {
                        clear()
                        close()
                    }
                }
                it.setIconResource(R.drawable.ic_check_24)
                it.text = getString(R.string.button_clear_ok)
            }
        }
    }
}