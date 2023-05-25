package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.app.AppInfo
import net.imshit.aircraftwar.databinding.ActivityMainBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate((layoutInflater))) {
            setContentView(root)

            amTb.setNavigationOnClickListener {
                AccountActivity.actionStart(this@MainActivity)
            }

            amTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfo.showAboutDialog(this@MainActivity)
                }
                return@setOnMenuItemClickListener true
            }

            // setup listener
            amBtnEasy.setOnClickListener {
                GameActivity.actionStart(this@MainActivity, Difficulty.EASY, amSwSound.isChecked)
            }
            amBtnMedium.setOnClickListener {
                GameActivity.actionStart(this@MainActivity, Difficulty.MEDIUM, amSwSound.isChecked)
            }
            amBtnHard.setOnClickListener {
                GameActivity.actionStart(this@MainActivity, Difficulty.HARD, amSwSound.isChecked)
            }
        }
    }
}