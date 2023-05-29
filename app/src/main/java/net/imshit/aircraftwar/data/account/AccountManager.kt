package net.imshit.aircraftwar.data.account

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.sync.Semaphore
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.MessageDigest

object AccountManager {

    private const val SALT = "HITsz520"
    private val BASE_URL = "https://haxiaoshen.top/"

    var isLogin: Boolean
        get() = false
        set(value) = TODO()

    private fun encodePassword(password: String): String {
        val salted = "$SALT$password$SALT"
        val bytes = salted.toByteArray(Charsets.UTF_8)
        val sha512 = MessageDigest.getInstance("SHA-512").digest(bytes)
        return sha512.joinToString(separator = "") { "%02x".format(it) }
    }

    @POST("login")
    suspend fun login(@Body account: String) {
        TODO()
    }

    private fun login(
        account: String,
        password: String,
        onSuccess: Runnable,
        onFail: Runnable,
        onFinish: Runnable
    ) {
//        val requestBody =
//            FormBody.Builder().add("user", account).add("password", encodePassword(password))
//                .build()
//        val request = Request.Builder().url(LOGIN_URL).post(requestBody).build()
//        httpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                onFail.run()
//                onFinish.run()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    // TODO
//                    onSuccess.run()
//                } else {
//                    onFail.run()
//                }
//                onFinish.run()
//            }
//        })
    }

    private fun register(
        account: String,
        password: String,
        onSuccess: Runnable,
        onFail: Runnable,
        onFinish: Runnable
    ) {
//        val requestBody =
//            FormBody.Builder().add("user", account).add("password", encodePassword(password))
//                .build()
//        val request = Request.Builder().url(REGISTER_URL).post(requestBody).build()
//        httpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                onFail.run()
//                onFinish.run()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    // TODO
//                    login(account, password, onSuccess, onFail, onFinish)
//                } else {
//                    onFail.run()
//                }
//                onFinish.run()
//            }
//        })
    }

    suspend fun requireLogin(context: Context): Boolean {
        if (!isLogin && context is AppCompatActivity) {
            val semaphore = Semaphore(1, 1)
            LoginDialog().apply {
                onDismissCallback = Runnable {
                    semaphore.release()
                }
                show(context.supportFragmentManager, "login")
            }
            semaphore.acquire()
        }
        return isLogin
    }
}