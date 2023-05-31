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