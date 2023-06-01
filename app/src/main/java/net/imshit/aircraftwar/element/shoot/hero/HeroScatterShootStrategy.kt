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

package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.logic.game.Games
import kotlin.math.absoluteValue

class HeroScatterShootStrategy(game: Games) : HeroShootStrategies(game = game) {
    override fun shoot(
        x: Float, y: Float, speedY: Float, power: Int
    ): List<HeroBullet> {
        val direction = -1
        val shootNum = 3
        val bulletCenterY = y + direction * 2
        val bulletCenterSpeedX = 0f
        val bulletCenterSpeedY = speedY + direction * 0.2f
        return mutableListOf<HeroBullet>().apply {
            for (i in 0 until shootNum) {
                add(
                    HeroBullet(
                        this@HeroScatterShootStrategy.game,
                        x + (i * 2 - shootNum + 1) * 20,
                        bulletCenterY + (i - shootNum / 2f).absoluteValue * 20,
                        bulletCenterSpeedX + (i * 2 - shootNum + 1) * 0.05f,
                        bulletCenterSpeedY,
                        power
                    )
                )
            }
        }.toList()
    }
}