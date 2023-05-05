package net.imshit.aircraftwar.element

import android.graphics.Bitmap
import net.imshit.aircraftwar.Param
import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import kotlin.math.abs

abstract class AbstractFlyingObject(
    var locationX: Float,
    var locationY: Float,
    val speedX: Float,
    val speedY: Float
) {
    private var isValid: Boolean = true
    protected abstract val image: Bitmap
    private val width = image.width
    private val height = image.height

    fun forward() {
        locationX += speedX * Param.REFRESH_INTERVAL
        locationY += speedY * Param.REFRESH_INTERVAL
        if (locationX < 0 || locationX >= Param.WINDOW_WIDTH || locationY >= Param.WINDOW_HEIGHT) {
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