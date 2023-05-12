package net.imshit.aircraftwar.util.dao

import android.content.Context
import net.imshit.aircraftwar.logic.data.Difficulty


class ScoreboardDaoSharedPreferences(context: Context, gameMode: Difficulty) : ScoreboardDao {
    private val sharedPreferences =
        context.getSharedPreferences("record-${gameMode.name}", Context.MODE_PRIVATE)

    private val buffer = mutableListOf<ScoreInfo>()

    init {
        val size = this.sharedPreferences.getInt("size", 0)
        for (i in 0 until size) {
            val name = this.sharedPreferences.getString("$i-name", "")!!
            val score = this.sharedPreferences.getInt("$i-score", 0)
            val time = this.sharedPreferences.getString("$i-time", "")!!
            this.buffer.add(ScoreInfo(name, score, time))
        }
        this.buffer.sortByDescending(ScoreInfo::score)
    }

    override fun getTopK(topK: Int): List<ScoreInfo> {
        return if (topK < 0) {
            this.buffer.toList()
        } else {
            this.buffer.subList(0, topK).toList()
        }
    }

    override fun addItem(item: ScoreInfo) {
        this.buffer.add(item)
        this.buffer.sortByDescending(ScoreInfo::score)
    }

    override fun deleteItem(item: ScoreInfo) {
        this.buffer.remove(item)
    }

    override fun deleteItem(indices: IntArray) {
        indices.sortedDescending().forEach(this.buffer::removeAt)
    }

    override fun deleteAll() {
        this.buffer.clear()
    }

    override fun close() {
        val editor = this.sharedPreferences.edit()
        val size = this.buffer.size

        editor.clear()
        editor.putInt("size", size)
        for (i in 0 until size) {
            val scoreInfo = this.buffer[i]
            editor.putString("$i-name", scoreInfo.name)
            editor.putInt("$i-score", scoreInfo.score)
            editor.putString("$i-time", scoreInfo.time)
        }
        editor.apply()
    }
}