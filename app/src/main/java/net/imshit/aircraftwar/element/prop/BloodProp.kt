package net.imshit.aircraftwar.element.prop

import net.imshit.aircraftwar.logic.Games

class BloodProp(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float
) : Props(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    override val image = game.images.propBlood
    override fun activate() {
        game.heroAircraft.increaseHp(100)
    }
}