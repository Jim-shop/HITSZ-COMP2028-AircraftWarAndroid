package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                    R.id.item_about ->
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setTitle(R.string.item_about)
                            .setIcon(R.drawable.ic_about_24)
                            .setMessage(R.string.app_about)
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .show()
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