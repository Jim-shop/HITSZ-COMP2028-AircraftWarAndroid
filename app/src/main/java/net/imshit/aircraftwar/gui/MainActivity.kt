package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.ActivityMainBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class MainActivity : AppCompatActivity() {

    var gameMode = Difficulty.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            amTb.setNavigationOnClickListener {
                AccountActivity.actionStart(this@MainActivity)
            }

            amTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            amMbtgMode.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
                if (isChecked) {
                    gameMode = when (checkedId) {
                        R.id.am_btn_easy -> Difficulty.EASY
                        R.id.am_btn_medium -> Difficulty.MEDIUM
                        R.id.am_btn_hard -> Difficulty.HARD
                        else -> Difficulty.EASY
                    }
                }
            }

            amBtnOffline.setOnClickListener {
                GameActivity.actionStart(this@MainActivity, gameMode, amSwSound.isChecked)
            }
        }
    }
}