package net.imshit.aircraftwar.data.pairing

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.imshit.aircraftwar.data.account.LoginManager
import net.imshit.aircraftwar.logic.game.Difficulty
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class PairingClient(
    private val context: Context,
    private val mode: Difficulty,
) : WebSocketListener() {

    companion object Const {
        const val SOCKET_URL = "wss://haxiaoshen.top/game/pairing"
    }

    private val httpClient = OkHttpClient.Builder().pingInterval(30, TimeUnit.SECONDS).build()

    private var webSocket: WebSocket? = null
    private val loginManager = LoginManager(context)

    fun run() {
        CoroutineScope(Dispatchers.Default).launch {
            if (loginManager.requireLogin()) {
                val request = Request.Builder()
                    .url("$SOCKET_URL?token=${loginManager.token}&mode=${mode.name.lowercase()}")
                    .build()
                webSocket = httpClient.newWebSocket(request, this@PairingClient)
            }
        }
    }

    var onChange: ((List<PairingInfo>) -> Unit)? = null
    var onSucceed: ((Int) -> Unit)? = null

    override fun onMessage(webSocket: WebSocket, text: String) {
        val jsonObject = JSONObject(text)
        val type = jsonObject.getString("type")
        if (type == "player") {
            val jsonArray = jsonObject.getJSONArray("msg")
            val players = mutableListOf<PairingInfo>()
            for (i in 0 until jsonArray.length()) {
                val jsonPlayer = jsonArray.getJSONObject(i)
                players.add(
                    PairingInfo(
                        jsonPlayer.getInt("ID"), jsonPlayer.getString("name"),
                        jsonPlayer.getBoolean("requesting")
                    )
                )
            }
            onChange?.invoke(players)
        } else if (type == "room") {
            val room = jsonObject.getInt("msg")
            onSucceed?.invoke(room)
        }
    }

    fun select(userId: Int) {
        webSocket?.send("$userId")
    }
}