package net.imshit.aircraftwar.element

import android.graphics.Bitmap
import net.imshit.aircraftwar.logic.game.Games
import kotlin.math.abs

abstract class AbstractFlyingObject(
    val game: Games,
    initialX: Float,
    initialY: Float,
    protected var speedX: Float,
    protected val speedY: Float
) {
    var x: Float = initialX
    var y: Float = initialY
    abstract val image: Bitmap
    val width: Float by lazy { this.image.width.toFloat() }
    val height: Float by lazy { this.image.height.toFloat() }
    protected open val boundingHeight: Float by lazy { this.height }
    var isValid: Boolean = true
        private set

    fun notValid() = !this.isValid

    open fun forward(timeMs: Int) {
        this.x += this.speedX * timeMs
        this.y += this.speedY * timeMs
        if (this.x < 0 || this.x >= this.game.width || y >= this.game.height) {
            vanish()
        }
    }

    fun vanish() {
        this.isValid = false
    }

    fun crash(flyingObject: AbstractFlyingObject): Boolean {
        val xLimit = (this.width + flyingObject.width) / 2
        val yLimit = (this.boundingHeight + flyingObject.boundingHeight) / 2
        return abs(this.x - flyingObject.x) < xLimit && abs(this.y - flyingObject.y) < yLimit
    }
}