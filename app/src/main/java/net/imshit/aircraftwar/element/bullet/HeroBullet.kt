package net.imshit.aircraftwar.element.bullet

import net.imshit.aircraftwar.logic.Games

class HeroBullet(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float, power: Int
) : AbstractBullet(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    power = power
) {
    override val image = game.images.bulletHero
}