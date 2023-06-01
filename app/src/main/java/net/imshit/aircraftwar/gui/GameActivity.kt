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
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.imshit.aircraftwar.data.scoreboard.ScoreboardAgent
import net.imshit.aircraftwar.databinding.ActivityGameBinding
import net.imshit.aircraftwar.logic.game.Difficulty
import net.imshit.aircraftwar.logic.game.Games

class GameActivity : AppCompatActivity() {
    companion object Api {
        fun actionStart(
            context: Context,
            gameMode: Difficulty,
            soundMode: Boolean,
            onlineMode: Boolean,
            roomId: Int = 0
        ) {
            context.startActivity(Intent(context, GameActivity::class.java).apply {
                putExtra("gameMode", gameMode)
                putExtra("soundMode", soundMode)
                putExtra("onlineMode", onlineMode)
                putExtra("roomId", roomId)
            })
        }
    }

    private fun Window.makeFullScreen() {
        setDecorFitsSystemWindows(false)
        WindowInsetsControllerCompat(this, this.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取配置
        val soundMode: Boolean
        val gameMode: Difficulty
        val onlineMode: Boolean
        val roomId: Int
        intent.apply {
            gameMode = getSerializableExtra("gameMode", Difficulty::class.java) ?: Difficulty.EASY
            soundMode = getBooleanExtra("soundMode", true)
            onlineMode = getBooleanExtra("onlineMode", true)
            roomId = getIntExtra("roomId", 0)
        }
        val game: Games = Games.getGames(this,
            gameMode,
            soundMode,
            onlineMode,
            roomId,
            object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    onGameOver(gameMode, msg.what, onlineMode)
                }
            })
        with(ActivityGameBinding.inflate(layoutInflater)) {
            root.addView(game, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            setContentView(root)
        }
        window.makeFullScreen()
    }

    private fun onGameOver(gameMode: Difficulty, score: Int, onlineMode: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            ScoreboardAgent(this@GameActivity, onlineMode, gameMode).apply {
                onFinishCallback = {
                    this@GameActivity.finish()
                }
            }.requireSave(score)
        }
    }
}