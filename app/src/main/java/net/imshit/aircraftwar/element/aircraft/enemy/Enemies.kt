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

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.element.event.EnemyListener
import net.imshit.aircraftwar.element.event.GameEvents
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.element.shoot.enemy.EnemyShootStrategies
import net.imshit.aircraftwar.element.shoot.enemy.EnemyShootStrategyFactory
import net.imshit.aircraftwar.logic.game.Games

sealed class Enemies(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    maxHp: Int,
    power: Int,
    shootNum: Int
) : AbstractAircraft(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    maxHp = maxHp,
    power = power,
), EnemyListener {
    open val credits: Int = 0

    open fun prop(): List<Props> {
        return listOf()
    }

    private val shootStrategyFactory = EnemyShootStrategyFactory(game)
    private lateinit var shootStrategy: EnemyShootStrategies

    init {
        setShootNum(shootNum)
    }

    final override fun setShootNum(shootNum: Int) {
        this.shootStrategy = shootStrategyFactory.getStrategy(shootNum)
    }

    override fun shoot(): List<EnemyBullet> {
        return this.shootStrategy.shoot(this.x, this.y, this.speedY, this.power)
    }

    override fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> vanish()
        }
    }
}
