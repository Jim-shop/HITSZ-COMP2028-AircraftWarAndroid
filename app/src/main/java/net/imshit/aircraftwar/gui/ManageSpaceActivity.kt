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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.app.AppInfoDialog
import net.imshit.aircraftwar.data.scoreboard.offline.ScoreboardDaoSharedPreferences
import net.imshit.aircraftwar.databinding.ActivityManageSpaceBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class ManageSpaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityManageSpaceBinding.inflate(layoutInflater)) {
            setContentView(root)

            amsTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            amsBtnClear.setOnClickListener {
                it as MaterialButton
                it.isEnabled = false
                listOf(
                    Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD
                ).forEach { gameMode ->
                    ScoreboardDaoSharedPreferences(this@ManageSpaceActivity, gameMode).clear()
                }
                it.setIconResource(R.drawable.ic_check_24)
                it.text = getString(R.string.button_clear_ok)
            }
        }
    }
}