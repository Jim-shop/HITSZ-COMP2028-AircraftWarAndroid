package net.imshit.aircraftwar.element.prop

import net.imshit.aircraftwar.logic.GameEvents
import net.imshit.aircraftwar.logic.game.Games

class BombProp(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float
) : Props(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    override val image = game.images.propBomb
    override fun activate() {
        game.notify(GameEvents.BOMB_ACTIVATE)
    }
}