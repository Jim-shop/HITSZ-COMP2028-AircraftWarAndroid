package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.account.AccountManager
import net.imshit.aircraftwar.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    companion object Api {
        fun actionStart(context: Context) {
            if (AccountManager.requireLogin(context)) {
                context.startActivity(Intent(context, AccountActivity::class.java).apply {
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        }
    }
}