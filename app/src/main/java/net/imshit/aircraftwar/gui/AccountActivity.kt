package net.imshit.aircraftwar.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.account.AccountManager
import net.imshit.aircraftwar.data.app.AppInfo
import net.imshit.aircraftwar.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
            AccountManager.showLoginDialog(context, onSuccess = {
                context.startActivity(Intent(context, AccountActivity::class.java).apply {
                })
            }, onFail = {})
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
                    R.id.item_about -> AppInfo.showAboutDialog(this@AccountActivity)
                }
                return@setOnMenuItemClickListener true
            }
        }
    }
}