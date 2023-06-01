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

package net.imshit.aircraftwar.element.aircraft.enemy

import net.imshit.aircraftwar.element.generate.prop.PropGenerateStrategies
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.game.Games

class EliteEnemy(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    maxHp: Int,
    power: Int,
    private val propGenerateStrategy: PropGenerateStrategies
) : Enemies(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    maxHp = maxHp,
    power = power,
    shootNum = 1
) {
    override val image = game.images.aircraftElite

    override fun prop(): List<Props> {
        return propGenerateStrategy.createProp(this.x, this.y)?.let { listOf(it) } ?: listOf()
    }

    override val credits = 60
}