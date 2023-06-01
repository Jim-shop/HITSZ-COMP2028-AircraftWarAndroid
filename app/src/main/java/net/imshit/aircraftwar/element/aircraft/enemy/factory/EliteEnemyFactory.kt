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

package net.imshit.aircraftwar.element.aircraft.enemy.factory

import net.imshit.aircraftwar.element.aircraft.enemy.EliteEnemy
import net.imshit.aircraftwar.element.generate.prop.PropGenerateStrategies
import net.imshit.aircraftwar.logic.game.Games

class EliteEnemyFactory(game: Games, private val propGenerateStrategy: PropGenerateStrategies) :
    EnemyFactories(game = game) {

    override fun createEnemy(hp: Int, power: Int, speed: Float): EliteEnemy {
        val x = (Math.random() * (game.width - game.images.aircraftElite.width)).toFloat()
        return EliteEnemy(game, x, 0f, 0f, speed, hp, power, this.propGenerateStrategy)
    }
}