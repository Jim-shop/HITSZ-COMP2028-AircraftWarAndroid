package net.imshit.aircraftwar.element.aircraft.hero

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.shoot.hero.HeroShootStrategies
import net.imshit.aircraftwar.element.shoot.hero.HeroShootStrategyFactory
import net.imshit.aircraftwar.logic.game.Games

class HeroAircraft(
    game: Games,
    maxHp: Int,
    power: Int,
    shootNum: Int
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
        // 英雄机由触控控制，不通过forward函数移动
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

    fun increaseHp(increment: Int) {
        if (increment > 0) {
            this.hp += increment
            if (this.hp > this.maxHp || this.hp < 0) {
                this.hp = this.maxHp
            }
        }
    }
}