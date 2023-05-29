package net.imshit.aircraftwar.data.account

import android.content.Context
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AccountDaoSharedPreferences(context: Context) : AccountDao {
    private val sharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE)

    override fun clear() {
        this.sharedPreferences.edit().clear().apply()
    }

    override fun store(accountInfo: AccountInfo) {
        val editor = this.sharedPreferences.edit()
        editor.clear()
        editor.putString("account", accountInfo.account)
        editor.putString("token", accountInfo.token)
        editor.putString(
            "loginTime", accountInfo.loginTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
        editor.apply()
    }

    override fun load(): AccountInfo? {
        val account = this.sharedPreferences.getString("account", null)
        val token = this.sharedPreferences.getString("token", null)
        val loginTime = this.sharedPreferences.getString("loginTime", null)
        return if (account == null || token == null || loginTime == null) {
            null
        } else {
            AccountInfo(
                account,
                token,
                LocalDateTime.parse(loginTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
        }
    }
}