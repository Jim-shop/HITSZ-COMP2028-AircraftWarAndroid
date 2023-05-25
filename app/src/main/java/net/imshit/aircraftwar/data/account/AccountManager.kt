package net.imshit.aircraftwar.data.account

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R

object AccountManager {
    fun showLoginDialog(context: Context, onSuccess: Runnable) {
        val dialogListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_NEGATIVE -> {}
                DialogInterface.BUTTON_POSITIVE -> {
                    // TODO()
                    onSuccess.run()
                }

                DialogInterface.BUTTON_NEUTRAL -> {
                    // TODO()
                    onSuccess.run()
                }
            }
        }
        MaterialAlertDialogBuilder(context).setTitle(R.string.dialog_login_title)
            .setIcon(R.drawable.ic_login_24).setPositiveButton(android.R.string.ok, dialogListener)
            .setNeutralButton(R.string.dialog_login_button_register, dialogListener)
            .setNegativeButton(android.R.string.cancel, dialogListener)
            .setView(R.layout.dialog_login).show()
    }
}