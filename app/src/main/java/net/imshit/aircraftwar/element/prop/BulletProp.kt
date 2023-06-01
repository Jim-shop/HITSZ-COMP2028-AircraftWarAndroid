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

package net.imshit.aircraftwar.element.prop

import net.imshit.aircraftwar.logic.game.Games
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.atomic.AtomicInteger

class BulletProp(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float
) : Props(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    private companion object Static {
        val usedCount = AtomicInteger(0)
    }

    override val image = game.images.propBullet

    override fun activate() {
        usedCount.getAndIncrement()
        val heroAircraft = game.heroAircraft
        heroAircraft.setShootNum(3)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (usedCount.decrementAndGet() <= 0) {
                    heroAircraft.setShootNum(1)
                }
            }
        }, 10000)
    }
}