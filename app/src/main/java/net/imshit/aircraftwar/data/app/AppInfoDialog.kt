package net.imshit.aircraftwar.data.app

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R

class AppInfoDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            MaterialAlertDialogBuilder(it).setTitle(R.string.item_about_long)
                .setIcon(R.drawable.ic_about_24).setMessage(R.string.app_about)
                .setPositiveButton(android.R.string.ok) { _, _ -> }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}