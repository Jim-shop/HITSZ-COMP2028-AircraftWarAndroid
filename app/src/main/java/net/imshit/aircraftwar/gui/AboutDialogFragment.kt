package net.imshit.aircraftwar.gui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import net.imshit.aircraftwar.R

class AboutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.about)
                .setMessage(R.string.app_about)
                .setPositiveButton(R.string.ok) { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}