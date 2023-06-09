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
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.data.app.AppInfoDialog
import net.imshit.aircraftwar.databinding.ActivityMainBinding
import net.imshit.aircraftwar.logic.game.Difficulty

class MainActivity : AppCompatActivity() {

    private var gameMode = Difficulty.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ActivityMainBinding.inflate(layoutInflater)) {
            setContentView(root)

            amTb.setNavigationOnClickListener {
                AccountActivity.actionStart(this@MainActivity)
            }

            amTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about -> AppInfoDialog().show(supportFragmentManager, "about")
                }
                return@setOnMenuItemClickListener true
            }

            amMbtgMode.addOnButtonCheckedListener { _, checkedId, isChecked ->
                if (isChecked) {
                    gameMode = when (checkedId) {
                        R.id.am_btn_easy -> Difficulty.EASY
                        R.id.am_btn_medium -> Difficulty.MEDIUM
                        R.id.am_btn_hard -> Difficulty.HARD
                        else -> Difficulty.EASY
                    }
                }
            }

            amBtnOffline.setOnClickListener {
                GameActivity.actionStart(this@MainActivity, gameMode, amSwSound.isChecked, false)
            }

            amBtnOnline.setOnClickListener {
                PairingActivity.actionStart(this@MainActivity, gameMode, amSwSound.isChecked)
            }
        }
    }
}