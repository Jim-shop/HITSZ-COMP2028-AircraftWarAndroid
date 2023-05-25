package net.imshit.aircraftwar.data.account

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.DialogLoginBinding
import java.net.URL

object AccountManager {
    private const val SALT = "HITsz520"
    private val REMOTE = URL("https://haxiaoshen.top/")

    fun login(account: String, password: String, onSuccess: Runnable) {
        // TODO
        onSuccess.run()
    }

    fun register(account: String, password: String, onSuccess: Runnable) {
        // TODO
        login(account, password, onSuccess)
    }

    fun showLoginDialog(context: Context, onSuccess: Runnable) {
        with(MaterialAlertDialogBuilder(context)) {
            with(DialogLoginBinding.inflate(LayoutInflater.from(context), null, false)) {
                val dialogListener = DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> ::login
                        DialogInterface.BUTTON_NEUTRAL -> ::register
                        else -> null
                    }?.invoke(
                        dlTietAc.text.toString(), dlTietPw.text.toString(), onSuccess
                    )
                }
                setView(root)
                setTitle(R.string.dialog_login_title)
                setIcon(R.drawable.ic_login_24)
                setPositiveButton(android.R.string.ok, dialogListener)
                setNeutralButton(R.string.dialog_login_button_register, dialogListener)
                setNegativeButton(android.R.string.cancel, dialogListener)
                show()
            }
        }
    }
}