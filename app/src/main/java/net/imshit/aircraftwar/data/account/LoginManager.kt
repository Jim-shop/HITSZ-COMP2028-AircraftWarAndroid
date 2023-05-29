package net.imshit.aircraftwar.data.account

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.sync.Semaphore
import java.security.MessageDigest
import java.time.Duration
import java.time.LocalDateTime

class LoginManager(context: Context) {

    companion object Conf {
        private const val SALT = "HITsz-Crazy-COMP2028"
        private val EXPIRE_INTERVAL = Duration.ofHours(24)

        private fun encodePassword(password: String): String {
            val salted = "$SALT$password$SALT"
            val bytes = salted.toByteArray(Charsets.UTF_8)
            val sha512 = MessageDigest.getInstance("SHA-256").digest(bytes)
            return sha512.joinToString(separator = "") { "%02x".format(it) }
        }
    }

    private val dao = AccountDaoSharedPreferences(context)
    private var accountInfo: AccountInfo? = null
        get() = field ?: kotlin.run {
            val loaded = dao.load()
            loaded?.run {
                field = if (loginTime.plus(EXPIRE_INTERVAL).isBefore(LocalDateTime.now())) {
                    dao.clear()
                    null
                } else {
                    loaded
                }
            }
            field
        }
        set(value) {
            field = value
            if (value == null) {
                dao.clear()
            } else {
                dao.store(value)
                Log.e("FFFFF", "store.")
            }
        }

    val isLogin: Boolean
        get() = accountInfo != null

    suspend fun login(account: String, password: String): Boolean {
        return try {
            val result = LoginApi.api.login(account, encodePassword(password))
            this.accountInfo = AccountInfo(
                account,
                result,
                LocalDateTime.now(),
            )
            true
        } catch (e: Exception) {
            this.accountInfo = null
            false
        }
    }

    suspend fun register(account: String, password: String): Boolean {
        return try {
            val result = LoginApi.api.register(account, encodePassword(password))
            this.accountInfo = AccountInfo(
                account,
                result,
                LocalDateTime.now(),
            )
            true
        } catch (e: Exception) {
            this.accountInfo = null
            false
        }
    }

    suspend fun requireLogin(context: Context): Boolean {
        if (!isLogin && context is AppCompatActivity) {
            val semaphore = Semaphore(1, 1)
            LoginDialog(this).apply {
                onDismissCallback = Runnable {
                    semaphore.release()
                }
            }.show(context.supportFragmentManager, "login")
            semaphore.acquire()
        }
        return isLogin
    }
}