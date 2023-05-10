package net.imshit.aircraftwar.element.aircraft.hero

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.element.shoot.hero.HeroShootStrategyFactory
import net.imshit.aircraftwar.logic.Games

class HeroAircraft private constructor(
    game: Games,
    maxHp: Int,
    power: Int,
    shootNum: Int
) : AbstractAircraft(
    game = game,
    initialX = game.screenWidth / 2f,
    initialY = game.screenHeight / 2f,
    speedX = 0f,
    speedY = 0f,
    maxHp = maxHp,
    power = power,
    shootStrategyFactory = HeroShootStrategyFactory(game),
    shootNum = shootNum
) {
    override val image = game.images.aircraftHero
    override fun forward(timeMs: Int) {
        // 英雄机由触控控制，不通过forward函数移动
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