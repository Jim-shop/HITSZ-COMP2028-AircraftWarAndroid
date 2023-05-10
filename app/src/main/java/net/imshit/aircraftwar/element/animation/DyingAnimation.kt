package net.imshit.aircraftwar.element.animation

import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.aircraft.AbstractAircraft

class DyingAnimation(
    aircraft: AbstractAircraft
) : AbstractFlyingObject(
    game = aircraft.game, initialX = aircraft.x, initialY = aircraft.y, speedX = 0f, speedY = 0f
) {
    override val image = aircraft.image
    // TODO
}