package net.imshit.aircraftwar.data.account

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.databinding.DialogLoginBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

object AccountManager {
    private const val SALT = "HITsz520"
    private val LOGIN_URL = URL("https://haxiaoshen.top/login")
    private val REGISTER_URL = URL("https://haxiaoshen.top/register")

    private val httpClient by lazy {
        OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS).build()
    }

    private fun encodePassword(password: String): String {
        val salted = "$SALT$password$SALT"
        val bytes = salted.toByteArray(Charsets.UTF_8)
        val sha512 = MessageDigest.getInstance("SHA-512").digest(bytes)
        return sha512.joinToString(separator = "") { "%02x".format(it) }
    }

    private fun login(account: String, password: String, onSuccess: Runnable, onFail: Runnable) {
        val requestBody =
            FormBody.Builder().add("user", account).add("password", encodePassword(password))
                .build()
        val request = Request.Builder().url(LOGIN_URL).post(requestBody).build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFail.run()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // TODO
                    onSuccess.run()
                } else {
                    onFail.run()
                }
            }
        })
    }

    private fun register(account: String, password: String, onSuccess: Runnable, onFail: Runnable) {
        val requestBody =
            FormBody.Builder().add("user", account).add("password", encodePassword(password))
                .build()
        val request = Request.Builder().url(REGISTER_URL).post(requestBody).build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFail.run()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // TODO
                    login(account, password, onSuccess, onFail)
                } else {
                    onFail.run()
                }
            }
        })
    }

    fun showLoginDialog(context: Context, onSuccess: Runnable, onFail: Runnable) {
        with(DialogLoginBinding.inflate(LayoutInflater.from(context), null, false)) {
            val dialogListener = DialogInterface.OnClickListener { _, which ->
                val account = dlTietAc.text.toString()
                val password = dlTietPw.text.toString()
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> ::login
                    DialogInterface.BUTTON_NEUTRAL -> ::register
                    else -> null
                }?.invoke(account, password, onSuccess, onFail)
            }
            MaterialAlertDialogBuilder(context).run {
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