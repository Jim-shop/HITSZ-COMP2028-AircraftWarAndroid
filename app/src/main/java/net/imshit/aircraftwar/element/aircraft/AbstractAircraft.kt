package net.imshit.aircraftwar.element.aircraft

import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.AbstractShootStrategyFactory

abstract class AbstractAircraft(locationX: Float, locationY: Float, speedX: Float, speedY: Float, protected var hp: Int, val power: Int, val strategyFactory: AbstractShootStrategyFactory, val shootNum: Int) :
    AbstractFlyingObject(
        locationX = locationX,
        locationY = locationY,
        speedX = speedX,
        speedY = speedY
    ) {

    protected val maxHp = hp
//    protected var shootStrategy = this.strategyFactory.getStrategy(shootNum)

//    fun setShootNum(shootNum: Int) {
//        this.shootStrategy = this.shootStrategy.getStrategy(shootNum)
//    }

    fun decreaseHp(decrease: Int) {
        if (decrease > 0) {
            this.hp -= decrease
            if (this.hp <= 0) {
                this.hp = 0
                vanish()
            }
        }
    }

//    fun shoot() = this.shootStrategy.shoot(this.locationX, this.locationY, this.speedY, this.power)

//    fun getAnimation() = DyingAnimation(this)

    // TODO:
}
