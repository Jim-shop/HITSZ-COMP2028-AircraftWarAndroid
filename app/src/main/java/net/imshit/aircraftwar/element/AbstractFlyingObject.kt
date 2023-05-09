package net.imshit.aircraftwar.element

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.gui.GameActivity
import kotlin.math.abs

abstract class AbstractFlyingObject(
    val game: GameActivity,
    protected var locationX: Float,
    protected var locationY: Float,
    protected var speedX: Float,
    protected var speedY: Float
) {
    protected abstract val image: Int
    private var isValid: Boolean = true
    private var width: Int = 0
    private var height: Int = 0

    fun forward() {
        locationX += speedX * game.refreshInterval
        locationY += speedY * game.refreshInterval
        if (locationX < 0 || locationX >= game.width || locationY >= game.height) {
            vanish()
        }
    }

    fun vanish() {
        isValid = false
    }

    fun crash(flyingObject: AbstractFlyingObject): Boolean {
        val thisHeight = if (this is AbstractAircraft) height / 2 else height
        val fHeight =
            if (flyingObject is AbstractAircraft) flyingObject.height / 2 else flyingObject.height
        val xLimit = (width + flyingObject.width) / 2f
        val yLimit = (thisHeight + fHeight) / 2f
        return abs(locationX - flyingObject.locationX) < xLimit && abs(locationY - flyingObject.locationY) < yLimit
    }
}