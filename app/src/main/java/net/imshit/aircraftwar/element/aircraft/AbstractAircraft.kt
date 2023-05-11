package net.imshit.aircraftwar.element.aircraft

import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.animation.DyingAnimation
import net.imshit.aircraftwar.element.bullet.Bullets
import net.imshit.aircraftwar.element.shoot.ShootStrategyFactories
import net.imshit.aircraftwar.logic.game.Games

abstract class AbstractAircraft(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    val maxHp: Int,
    val power: Int,
    val shootStrategyFactory: ShootStrategyFactories,
    shootNum: Int
) : AbstractFlyingObject(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {

    override val boundingHeight = super.boundingHeight / 2

    var hp: Int = this.maxHp
        protected set
    protected var shootStrategy = this.shootStrategyFactory.getStrategy(shootNum)
        private set

    fun setShootNum(shootNum: Int) {
        this.shootStrategy = this.shootStrategyFactory.getStrategy(shootNum)
    }

    fun decreaseHp(decrease: Int) {
        if (decrease > 0) {
            this.hp -= decrease
            if (this.hp <= 0) {
                this.hp = 0
                vanish()
            }
        }
    }

    fun shoot(): List<Bullets> = this.shootStrategy.shoot(this.x, this.y, this.speedY, this.power)

    val animation: DyingAnimation
        get() = DyingAnimation(this)
}
