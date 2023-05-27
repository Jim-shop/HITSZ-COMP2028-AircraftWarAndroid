package net.imshit.aircraftwar.gui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.imshit.aircraftwar.R

class LoginDialog : AppCompatDialogFragment() {
    private var dialogView: View? = null

    private suspend fun login(account: String, password: String): Boolean {
        delay(1000)
        return false
    }

    private suspend fun register(account: String, password: String): Boolean {
        delay(1000 )
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_login, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): AppCompatDialog {
        return activity?.let { activity ->
            onCreateView(requireActivity().layoutInflater, null, savedInstanceState)?.let { view ->
                this.dialogView = view
                MaterialAlertDialogBuilder(activity).apply {
                    setView(view)
                    setTitle(R.string.dialog_login_title).setIcon(R.drawable.ic_login_24)
                    setPositiveButton(android.R.string.ok, null)
                    setNeutralButton(R.string.dialog_login_button_register, null)
                    setNegativeButton(android.R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                }.create()
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun getView(): View? {
        return this.dialogView
    }

    override fun onResume() {
        super.onResume()
        (dialog as? AlertDialog)?.apply {
            getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
                it.isEnabled = false
                val account =
                    view?.findViewById<TextInputEditText>(R.id.dl_tiet_ac)?.text.toString()
                val password =
                    view?.findViewById<TextInputEditText>(R.id.dl_tiet_pw)?.text.toString()
                runBlocking {
                    val isSuccess = async { login(account, password) }
                    if (isSuccess.await()) {
                        dismiss()
                        // TODO
                    } else {

                        // TODO
                    }
                }
            }
        }
    }
}