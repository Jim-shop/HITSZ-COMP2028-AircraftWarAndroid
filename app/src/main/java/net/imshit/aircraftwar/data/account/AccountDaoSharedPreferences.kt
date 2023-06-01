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
            "loginTime", accountInfo.loginTime.format(DateTimeFormatter.ISO_DATE_TIME)
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
                LocalDateTime.parse(loginTime, DateTimeFormatter.ISO_DATE_TIME)
            )
        }
    }
}