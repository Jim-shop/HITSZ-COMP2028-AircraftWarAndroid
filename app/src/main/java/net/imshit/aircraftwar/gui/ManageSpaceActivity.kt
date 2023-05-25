package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
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
                    R.id.item_about -> MaterialAlertDialogBuilder(this@ManageSpaceActivity).setTitle(
                        R.string.item_about_long
                    ).setIcon(R.drawable.ic_about_24).setMessage(R.string.app_about)
                        .setPositiveButton(android.R.string.ok) { _, _ -> }.show()
                }
                return@setOnMenuItemClickListener true
            }

            amsBtnClear.setOnClickListener {
                amsBtnClear.isEnabled = false
                listOf(
                    Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD
                ).forEach { gameMode ->
                    ScoreboardDaoSharedPreferences(this@ManageSpaceActivity, gameMode).apply {
                        clear()
                        close()
                    }
                }
                amsBtnClear.icon =
                    AppCompatResources.getDrawable(this@ManageSpaceActivity, R.drawable.ic_check_24)
                amsBtnClear.text = getString(R.string.button_clear_ok)
            }
        }
    }
}