package net.imshit.aircraftwar.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.ActivityManageSpaceBinding

class ManageSpaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityManageSpaceBinding.inflate(layoutInflater)) {
            setContentView(root)

            amsTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about ->
                        MaterialAlertDialogBuilder(this@ManageSpaceActivity)
                            .setTitle(R.string.item_about)
                            .setIcon(R.drawable.ic_about_24)
                            .setMessage(R.string.app_about)
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .show()
                }
                return@setOnMenuItemClickListener true
            }

            // TODO 有了存储逻辑之后记得加入
        }
    }
}