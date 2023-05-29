package net.imshit.aircraftwar.data.account

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.widget.doOnTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.DialogLoginBinding

class LoginDialog : AppCompatDialogFragment() {
    private var dialogView: View? = null

    private suspend fun login(account: String, password: String): Boolean {
        TODO()
    }

    private suspend fun register(account: String, password: String): Boolean {
        TODO()
    }

    var onDismissCallback: Runnable? = null

    override fun onDismiss(dialog: DialogInterface) {
        onDismissCallback?.run()
        super.onDismiss(dialog)
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
                    setNegativeButton(android.R.string.cancel, null)
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
            view?.let { DialogLoginBinding.bind(it) }?.apply {
                val loginButton = getButton(Dialog.BUTTON_POSITIVE)
                val registerButton = getButton(Dialog.BUTTON_NEUTRAL)
                val cancelButton = getButton(Dialog.BUTTON_NEGATIVE)

                val interactiveComponents = listOf(loginButton, registerButton, dlTilAc, dlTilPw)
                val jobs = mutableListOf<Job>()

                val progress = IndeterminateDrawable.createCircularDrawable(
                    context, CircularProgressIndicatorSpec(context, null)
                )
                val onSubmit = View.OnClickListener {
                    it as MaterialButton
                    interactiveComponents.forEach { c -> c.isEnabled = false }
                    it.icon = progress
                    val account = dlTietAc.text.toString()
                    val password = dlTietPw.text.toString()
                    jobs += CoroutineScope(Dispatchers.Default).launch {
                        val isSuccess = when (it) {
                            loginButton -> login(account, password)
                            registerButton -> register(account, password)
                            else -> true
                        }
                        if (isSuccess) {
                            dismiss()
                        } else {
                            withContext(Dispatchers.Main) {
                                interactiveComponents.forEach { c -> c.isEnabled = true }
                                it.icon = null
                                dlTilAc.error = getString(R.string.dialog_login_input_account_error)
                                dlTilPw.error =
                                    getString(R.string.dialog_login_input_password_error)
                            }
                        }
                    }
                }
                val submitButtons = listOf(loginButton, registerButton)
                submitButtons.forEach { button -> button.setOnClickListener(onSubmit) }

                var isAccountValid = false
                var isPasswordValid = false
                val onChange = {
                    submitButtons.forEach { button ->
                        dlTilAc.error = null
                        dlTilPw.error = null
                        button.isEnabled = isAccountValid && isPasswordValid
                    }
                }
                dlTietAc.doOnTextChanged { text, _, _, _ ->
                    isAccountValid = (text?.length in 6..16)
                    onChange()
                }
                dlTietPw.doOnTextChanged { text, _, _, _ ->
                    isPasswordValid = (text?.length in 6..16)
                    onChange()
                }

                cancelButton.setOnClickListener {
                    jobs.forEach(Job::cancel)
                    dismiss()
                }

                onChange()
            }
        }
    }
}