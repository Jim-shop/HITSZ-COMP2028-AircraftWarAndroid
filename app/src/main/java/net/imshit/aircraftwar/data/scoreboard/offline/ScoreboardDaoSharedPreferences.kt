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

package net.imshit.aircraftwar.data.scoreboard.offline

import android.content.Context
import net.imshit.aircraftwar.data.scoreboard.ScoreInfo
import net.imshit.aircraftwar.data.scoreboard.ScoreboardDao
import net.imshit.aircraftwar.logic.game.Difficulty
import java.util.Date
import kotlin.math.max


class ScoreboardDaoSharedPreferences(context: Context, gameMode: Difficulty) : ScoreboardDao {
    private val sharedPreferences =
        context.getSharedPreferences("record-${gameMode.name}", Context.MODE_PRIVATE)

    private val buffer = mutableListOf<ScoreInfo>()
    private var maxId = 0

    init {
        this.load()
    }

    override fun getTopK(topK: Int): List<ScoreInfo> {
        return if (topK < 0) {
            this.buffer.toList()
        } else {
            this.buffer.subList(0, topK).toList()
        }
    }

    override fun addItem(item: ScoreInfo) {
        this.buffer.add(
            ScoreInfo(
                ++maxId,
                item.name,
                item.score,
                item.time
            )
        )
        this.reload()
    }

    override fun deleteItem(id: Int): Boolean {
        this.buffer.removeIf { it.id == id }
        this.reload()
        return true
    }

    fun clear() {
        this.buffer.clear()
        this.writeBack()
    }

    private fun reload() {
        this.writeBack()
        this.load()
    }

    private fun load() {
        this.buffer.clear()
        val size = this.sharedPreferences.getInt("size", 0)
        for (i in 0 until size) {
            val id = this.sharedPreferences.getInt("$i-id", 0)
            val name = this.sharedPreferences.getString("$i-name", "")!!
            val score = this.sharedPreferences.getInt("$i-score", 0)
            val time = Date(this.sharedPreferences.getLong("$i-time", 0L))
            maxId = max(id, maxId)
            this.buffer.add(ScoreInfo(id, name, score, time))
        }
    }

    private fun writeBack() {
        this.buffer.sortByDescending(ScoreInfo::score)

        val editor = this.sharedPreferences.edit()
        val size = this.buffer.size

        editor.clear()
        editor.putInt("size", size)
        for (i in 0 until size) {
            val scoreInfo = this.buffer[i]
            editor.putInt("$i-id", scoreInfo.id)
            editor.putString("$i-name", scoreInfo.name)
            editor.putInt("$i-score", scoreInfo.score)
            editor.putLong(
                "$i-time",
                scoreInfo.time.time
            )
        }
        editor.apply()
    }
}