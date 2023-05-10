package net.imshit.aircraftwar.element.prop.factory

import net.imshit.aircraftwar.element.prop.BloodProp
import net.imshit.aircraftwar.logic.Games

class BloodPropFactory(game: Games) : PropFactories(game = game) {
    override fun createProp(x: Float, y: Float) =
        BloodProp(this.game, x, y, this.speedX, this.speedY)
}