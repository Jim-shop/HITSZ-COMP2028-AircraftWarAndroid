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