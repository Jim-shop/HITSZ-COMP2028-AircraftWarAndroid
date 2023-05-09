package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.Difficulty
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityMainBinding.inflate((layoutInflater))) {
            setContentView(root)

            amTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AboutDialogFragment().show(
                        supportFragmentManager,
                        "about_dialog"
                    )
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