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

package net.imshit.aircraftwar.data.scoreboard

import androidx.appcompat.app.AppCompatActivity
import net.imshit.aircraftwar.data.account.LoginManager
import net.imshit.aircraftwar.data.scoreboard.offline.SaveScoreDialog
import net.imshit.aircraftwar.data.scoreboard.offline.ScoreboardDaoSharedPreferences
import net.imshit.aircraftwar.data.scoreboard.online.ScoreboardDaoOnline
import net.imshit.aircraftwar.gui.ScoreboardActivity
import net.imshit.aircraftwar.logic.game.Difficulty
import java.util.Date

class ScoreboardAgent(
    private val activity: AppCompatActivity,
    private val onlineMode: Boolean,
    private val gameMode: Difficulty
) : ScoreboardDao by if (onlineMode) ScoreboardDaoOnline(
    activity, gameMode
) else ScoreboardDaoSharedPreferences(activity, gameMode) {
    var onFinishCallback: (() -> Unit)? = null
    private val loginManager = if (onlineMode) LoginManager(activity) else null

    fun requireSave(score: Int) {
        if (onlineMode && loginManager != null) {
            if (loginManager.isLogin) {
                saveAndStart(loginManager.name!!, score)
            }
            onFinishCallback?.invoke()
        } else {
            SaveScoreDialog().apply {
                onSubmitCallback = { name ->
                    saveAndStart(name, score)
                }
                this.onFinishCallback = this@ScoreboardAgent.onFinishCallback
            }.show(activity.supportFragmentManager, "save_score")
        }
    }

    private fun saveAndStart(name: String, score: Int) {
        this.addItem(
            ScoreInfo(0, name, score, Date(System.currentTimeMillis()))
        )
        ScoreboardActivity.actionStart(this@ScoreboardAgent.activity, gameMode, onlineMode)
    }
}