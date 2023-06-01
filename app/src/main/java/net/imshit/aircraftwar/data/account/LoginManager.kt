/*
 * Copyright (c) [2023] [Jim-shop]
 * [AircraftwarAndroid] is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */

package net.imshit.aircraftwar.data.account

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.sync.Semaphore
import java.security.MessageDigest
import java.time.Duration
import java.time.LocalDateTime

class LoginManager(private val context: Context) {

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
            }
        }

    val isLogin: Boolean
        get() = accountInfo != null

    val token: String?
        get() = accountInfo?.token

    val name: String?
        get() = accountInfo?.account

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

    suspend fun requireLogin(): Boolean {
        if (!isLogin && context is AppCompatActivity) {
            // 唤起登录
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