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

package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.account.AccountDao
import net.imshit.aircraftwar.data.account.AccountDaoSharedPreferences
import net.imshit.aircraftwar.data.account.LoginManager
import net.imshit.aircraftwar.data.app.AppInfoDialog
import net.imshit.aircraftwar.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    companion object Api {
        fun actionStart(context: Context) {
            CoroutineScope(Dispatchers.Default).launch {
                if (LoginManager(context).requireLogin()) {
                    context.startActivity(Intent(context, AccountActivity::class.java).apply {

                    })
                }
            }
        }
    }

    private lateinit var dao: AccountDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.dao = AccountDaoSharedPreferences(this)

        with(ActivityAccountBinding.inflate((layoutInflater))) {
            setContentView(root)

            aaTb.setNavigationOnClickListener {
                this@AccountActivity.finish()
            }

            aaTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            aaTvName.text = dao.load()?.account

            aaBtnLogout.setOnClickListener {
                dao.clear()
                this@AccountActivity.finish()
            }
        }
    }
}