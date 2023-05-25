package net.imshit.aircraftwar.gui

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.ActivityMainBinding
import net.imshit.aircraftwar.logic.data.Difficulty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate((layoutInflater))) {
            setContentView(root)

            val listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> TODO()
                    DialogInterface.BUTTON_NEGATIVE -> TODO()
                    DialogInterface.BUTTON_NEUTRAL -> TODO()
                }
            }
            amTb.setNavigationOnClickListener {
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle(R.string.dialog_login_title)
                    .setIcon(R.drawable.ic_login_24)
                    .setPositiveButton(android.R.string.ok, listener)
                    .setNeutralButton(R.string.dialog_login_button_register, listener)
                    .setNegativeButton(android.R.string.cancel, listener)
                    .setView(R.layout.dialog_login).show()
            }

            amTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about ->
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setTitle(R.string.item_about_long)
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