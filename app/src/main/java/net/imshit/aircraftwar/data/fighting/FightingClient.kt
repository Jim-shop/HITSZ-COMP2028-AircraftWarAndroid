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
    private val context: Context,
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