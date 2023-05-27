package net.imshit.aircraftwar.element.aircraft

import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.animation.DyingAnimation
import net.imshit.aircraftwar.element.bullet.Bullets
import net.imshit.aircraftwar.logic.game.Games

abstract class AbstractAircraft(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    val maxHp: Int,
    val power: Int,
) : AbstractFlyingObject(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {

    override val boundingHeight: Float by lazy { super.boundingHeight / 2 }

    var hp: Int = this.maxHp
        protected set

    abstract fun setShootNum(shootNum: Int)

    open fun decreaseHp(decrease: Int) {
        if (decrease > 0) {
            this.hp -= decrease
            if (this.hp <= 0) {
                this.hp = 0
                vanish()
            }
        }
    }

    abstract fun shoot(): List<Bullets>

    val animation: DyingAnimation
        get() = DyingAnimation(this)
}
