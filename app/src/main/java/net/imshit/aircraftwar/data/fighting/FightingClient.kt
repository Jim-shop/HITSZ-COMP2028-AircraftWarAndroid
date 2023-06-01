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

package net.imshit.aircraftwar.data.fighting

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.imshit.aircraftwar.data.account.LoginManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class FightingClient(
    context: Context,
    private val roomId: Int,
) : WebSocketListener() {

    companion object Const {
        const val SOCKET_URL = "wss://haxiaoshen.top/game/fighting"
    }

    private val httpClient = OkHttpClient.Builder().pingInterval(30, TimeUnit.SECONDS).build()

    private var webSocket: WebSocket? = null
    private val loginManager = LoginManager(context)

    fun run() {
        CoroutineScope(Dispatchers.Default).launch {
            if (loginManager.requireLogin()) {
                val request =
                    Request.Builder().url("$SOCKET_URL/$roomId?token=${loginManager.token}")
                        .build()
                webSocket = httpClient.newWebSocket(request, this@FightingClient)
            }
        }
    }

    var onData: ((CommunicateInfo) -> Unit)? = null
    var onQuit: (() -> Unit)? = null

    override fun onMessage(webSocket: WebSocket, text: String) {
        val jsonObject = JSONObject(text)
        val type = jsonObject.getString("type")
        if (type == "comm") {
            val jsonInfo = jsonObject.getJSONObject("msg")
            val userId = jsonInfo.getInt("user")
            val score = jsonInfo.getInt("score")
            val life = jsonInfo.getInt("life")
            onData?.invoke(
                CommunicateInfo(
                    userId, score, life
                )
            )
        } else if (type == "quit") {
            onQuit?.invoke()
        }
    }

    fun send(info: CommunicateInfo) {
        webSocket?.send("""{"user":${info.userId}, "score":${info.score}, "life":${info.life}}""")
    }
}