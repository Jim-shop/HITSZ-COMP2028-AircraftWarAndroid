package net.imshit.aircraftwar.element

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.gui.GameActivity
import kotlin.math.abs

abstract class AbstractFlyingObject(
    private val game: GameActivity,
    initialLocationX: Float,
    initialLocationY: Float,
    protected val speedX: Float,
    protected val speedY: Float
) {
    var locationX = initialLocationX
        protected set
    var locationY = initialLocationY
        protected set
    protected abstract val image: Int
    protected abstract val width: Int
    protected abstract var height: Int
    private var isValid: Boolean = true

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
        // TODO: 这里太丑了，改成每个object维护一个boundingBox
        val thisHeight = if (this is AbstractAircraft) height / 2 else height
        val fHeight =
            if (flyingObject is AbstractAircraft) flyingObject.height / 2 else flyingObject.height
        val xLimit = (width + flyingObject.width) / 2f
        val yLimit = (thisHeight + fHeight) / 2f
        return abs(locationX - flyingObject.locationX) < xLimit && abs(locationY - flyingObject.locationY) < yLimit
    }
}