package net.imshit.aircraftwar.data.scoreboard.online

import android.content.Context
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import kotlinx.coroutines.runBlocking
import net.imshit.aircraftwar.data.account.LoginManager
import net.imshit.aircraftwar.data.scoreboard.ScoreInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreboardDao
import net.imshit.aircraftwar.logic.game.Difficulty


class ScoreboardDaoOnline(context: Context, private val gameMode: Difficulty) : ScoreboardDao {
    private var buffer = mutableListOf<ScoreInfoOnline>()
    private val loginManager = LoginManager(context)

    override fun getTopK(topK: Int): List<ScoreInfo> {
        this.load()
        val result = mutableListOf<ScoreInfo>()
        for (item in buffer) {
            result.add(
                ScoreInfo(
                    item.id,
                    item.user,
                    item.score,
                    item.time
                )
            )
        }
        return if (topK < 0) {
            result.toList()
        } else {
            result.subList(0, topK).toList()
        }
    }

    override fun addItem(item: ScoreInfo) {
        loginManager.token?.let { token ->
            runBlocking {
                try {
                    ScoreboardApi.api.send(
                        token,
                        item.score,
                        gameMode.name.lowercase(),
                        Rfc3339DateJsonAdapter().toJson(item.time).let {
                            it.substring(1, it.length - 1)
                        }
                    )
                } catch (_: Exception) {
                }
            }
            this.load()
        }
    }

    override fun deleteItem(id: Int): Boolean {
        var isSucceed = false
        loginManager.token?.let { token ->
            runBlocking {
                try {
                    ScoreboardApi.api.delete(id, token)
                    isSucceed = true
                } catch (_: Exception) {
                }
            }
        }
        return isSucceed
    }

    private fun load() {
        loginManager.token?.let { token ->
            runBlocking {
                try {
                    buffer = ScoreboardApi.api.get(token, gameMode.name.lowercase()).toMutableList()
                    buffer.sortByDescending(ScoreInfoOnline::score)
                } catch (_: Exception) {
                }
            }
        }
    }
}