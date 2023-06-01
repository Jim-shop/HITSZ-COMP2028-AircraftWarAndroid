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

package net.imshit.aircraftwar.element.aircraft.hero

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.shoot.hero.HeroShootStrategies
import net.imshit.aircraftwar.element.shoot.hero.HeroShootStrategyFactory
import net.imshit.aircraftwar.logic.game.Games

class HeroAircraft(
    game: Games, maxHp: Int, power: Int, shootNum: Int, private val controller: HeroController
) : AbstractAircraft(
    game = game,
    initialX = game.width / 2f,
    initialY = game.height / 2f,
    speedX = 0f,
    speedY = 0f,
    maxHp = maxHp,
    power = power
) {
    override val image = game.images.aircraftHero
    override fun forward(timeMs: Int) {
        this.controller.calcForward(timeMs)
        this.x = this.controller.x
        this.y = this.controller.y
    }

    private val shootStrategyFactory = HeroShootStrategyFactory(game)
    private lateinit var shootStrategy: HeroShootStrategies

    init {
        setShootNum(shootNum)
    }

    override fun setShootNum(shootNum: Int) {
        this.shootStrategy = this.shootStrategyFactory.getStrategy(shootNum)
    }

    override fun shoot(): List<HeroBullet> {
        return this.shootStrategy.shoot(this.x, this.y, this.speedY, this.power)
    }

    override fun decreaseHp(decrease: Int) {
        super.decreaseHp(decrease)
        this.controller.onHit()
    }

    fun increaseHp(increment: Int) {
        if (increment > 0) {
            this.hp += increment
            if (this.hp > this.maxHp || this.hp < 0) {
                this.hp = this.maxHp
            }
        }
    }
}