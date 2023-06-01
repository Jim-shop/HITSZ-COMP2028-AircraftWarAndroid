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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.account.LoginManager
import net.imshit.aircraftwar.data.app.AppInfoDialog
import net.imshit.aircraftwar.data.pairing.PairingClient
import net.imshit.aircraftwar.data.pairing.PairingInfo
import net.imshit.aircraftwar.databinding.ActivityPairingBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class PairingActivity : AppCompatActivity() {
    companion object Api {
        fun actionStart(context: Context, gameMode: Difficulty, soundMode: Boolean) {
            CoroutineScope(Dispatchers.Default).launch {
                if (LoginManager(context).requireLogin()) {
                    context.startActivity(Intent(context, PairingActivity::class.java).apply {
                        putExtra("gameMode", gameMode)
                        putExtra("soundMode", soundMode)
                    })
                }
            }
        }
    }

    class PairingInfoAdapter(
        private val activity: AppCompatActivity,
        private val client: PairingClient
    ) : RecyclerView.Adapter<PairingInfoAdapter.PairingInfoViewHolder>() {
        class PairingInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val avatar: ImageView = itemView.findViewById(R.id.pvi_iv)
            private val accountView: TextView = itemView.findViewById(R.id.pvi_tv)
            private val button: MaterialButton = itemView.findViewById(R.id.pvi_btn)
            fun bind(pairingInfo: PairingInfo, context: Context, client: PairingClient) {
                this.accountView.text = pairingInfo.userAccount
                if (pairingInfo.isWilling) {
                    this.avatar.setImageResource(R.drawable.avatar)
                }
                this.button.setOnClickListener {
                    client.select(pairingInfo.userId)
                    this.button.text = context.getString(R.string.button_pairing_sent)
                    this.button.setIconResource(R.drawable.ic_check_24)
                }
            }
        }

        init {
            client.onChange = ::onDataChange
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairingInfoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pairing_view_item, parent, false)
            return PairingInfoViewHolder(view)
        }

        private val buffer = mutableListOf<PairingInfo>()

        override fun getItemCount(): Int = buffer.size

        override fun onBindViewHolder(holder: PairingInfoViewHolder, position: Int) {
            holder.bind(buffer[position], activity, client)
        }

        private fun onDataChange(data: List<PairingInfo>) {
            // 清除已下线
            buffer.removeIf {
                data.all { newItem -> newItem.userId != it.userId }
            }
            // 更新现有
            buffer.forEach { oldItem ->
                val newItem = data.find { newItem -> newItem.userId == oldItem.userId }
                newItem?.let {
                    oldItem.isWilling = it.isWilling
                }
            }
            // 新增新上线
            buffer.addAll(data.filter { buffer.all { oldItem -> oldItem.userId != it.userId } })
            activity.runOnUiThread {
                notifyDataSetChanged()
            }
        }
    }

    private var gameMode: Difficulty = Difficulty.EASY
    private var soundMode: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取配置
        intent.apply {
            gameMode = getSerializableExtra("gameMode", Difficulty::class.java) ?: Difficulty.EASY
            soundMode = getBooleanExtra("soundMode", true)
        }
        with(ActivityPairingBinding.inflate(layoutInflater)) {
            setContentView(root)

            apTb.setNavigationOnClickListener {
                this@PairingActivity.finish()
            }

            apTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            val client = PairingClient(this@PairingActivity, gameMode)
            apRv.adapter = PairingInfoAdapter(this@PairingActivity, client)
            apRv.setHasFixedSize(true)
            client.onSucceed = ::onSucceed
            client.run()
        }
    }

    private fun onSucceed(room: Int) {
        GameActivity.actionStart(this, gameMode, soundMode, true, room)
        this.finish()
    }
}