package net.imshit.aircraftwar.element.prop.factory

import net.imshit.aircraftwar.element.prop.BulletProp
import net.imshit.aircraftwar.logic.Games

class BulletPropFactory(game: Games) : PropFactories(game = game) {
    override fun createProp(x: Float, y: Float) =
        BulletProp(this.game, x, y, this.speedX, this.speedY)
}