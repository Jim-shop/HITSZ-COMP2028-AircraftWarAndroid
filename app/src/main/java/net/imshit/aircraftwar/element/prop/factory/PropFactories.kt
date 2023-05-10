package net.imshit.aircraftwar.element.prop.factory

import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.Games

abstract class PropFactories(val game: Games) {
    val speedX = 0f
    val speedY = 0.1f

    abstract fun createProp(x: Float, y: Float): Props?
}