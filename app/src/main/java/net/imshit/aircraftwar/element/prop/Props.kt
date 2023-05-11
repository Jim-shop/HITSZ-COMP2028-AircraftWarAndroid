package net.imshit.aircraftwar.element.prop

import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.logic.game.Games

sealed class Props(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float
) : AbstractFlyingObject(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    abstract fun activate()
}