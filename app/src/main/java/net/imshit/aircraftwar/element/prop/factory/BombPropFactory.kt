package net.imshit.aircraftwar.element.prop.factory

import net.imshit.aircraftwar.element.prop.BombProp
import net.imshit.aircraftwar.logic.game.Games

class BombPropFactory(game: Games) : PropFactories(game = game) {
    override fun createProp(x: Float, y: Float) =
        BombProp(this.game, x, y, this.speedX, this.speedY)
}