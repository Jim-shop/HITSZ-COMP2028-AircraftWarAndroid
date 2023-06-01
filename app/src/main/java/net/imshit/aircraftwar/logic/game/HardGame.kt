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

package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import net.imshit.aircraftwar.element.generate.enemy.HardEnemyGenerateStrategy

class HardGame(
    context: Context,
    attrs: AttributeSet?,
    soundMode: Boolean,
    onlineMode: Boolean,
    roomId: Int,
    handler: Handler
) :
    Games(
        context = context,
        attrs = attrs,
        soundMode = soundMode,
        onlineMode = onlineMode,
        roomId = roomId,
        handler = handler
    ) {

    override val generateStrategy = HardEnemyGenerateStrategy(this)

    override fun init() {
        super.init()
        this.background = images.backgroundHard
    }
}