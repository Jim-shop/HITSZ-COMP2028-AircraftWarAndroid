package net.imshit.aircraftwar.data.app

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R

object AppInfo {
    fun showAboutDialog(context: Context) {
        MaterialAlertDialogBuilder(context).setTitle(R.string.item_about_long)
            .setIcon(R.drawable.ic_about_24).setMessage(R.string.app_about)
            .setPositiveButton(android.R.string.ok) { _, _ -> }.show()
    }
}