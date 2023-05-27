package net.imshit.aircraftwar.data.account

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.gui.LoginDialog
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
    var isLogin: Boolean
        get() = false
        set(value) = TODO()

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

    private fun login(
        account: String,
        password: String,
        onSuccess: Runnable,
        onFail: Runnable,
        onFinish: Runnable
    ) {
        val requestBody =
            FormBody.Builder().add("user", account).add("password", encodePassword(password))
                .build()
        val request = Request.Builder().url(LOGIN_URL).post(requestBody).build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFail.run()
                onFinish.run()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // TODO
                    onSuccess.run()
                } else {
                    onFail.run()
                }
                onFinish.run()
            }
        })
    }

    private fun register(
        account: String,
        password: String,
        onSuccess: Runnable,
        onFail: Runnable,
        onFinish: Runnable
    ) {
        val requestBody =
            FormBody.Builder().add("user", account).add("password", encodePassword(password))
                .build()
        val request = Request.Builder().url(REGISTER_URL).post(requestBody).build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFail.run()
                onFinish.run()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // TODO
                    login(account, password, onSuccess, onFail, onFinish)
                } else {
                    onFail.run()
                }
                onFinish.run()
            }
        })
    }

    fun requireLogin(context: Context): Boolean {
        return if (isLogin) {
            true
        } else if (context is AppCompatActivity) {
            LoginDialog().apply {
                show(context.supportFragmentManager, "login")
                Log.e("FFFFF", view.toString())
            }
//            TODO()
            false
        } else {
            false
        }
    }
}