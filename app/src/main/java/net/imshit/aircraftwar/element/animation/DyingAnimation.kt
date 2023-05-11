package net.imshit.aircraftwar.element.animation

import android.graphics.Bitmap
import android.graphics.Color
import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.element.aircraft.AbstractAircraft

class DyingAnimation(
    aircraft: AbstractAircraft
) : AbstractFlyingObject(
    game = aircraft.game, initialX = aircraft.x, initialY = aircraft.y, speedX = 0f, speedY = 0f
) {

    val ALPHA = 0x80
    val LIVE_MS = 500
    val FLASH_MS = 100

    fun makeLightImage(image: Bitmap): Bitmap {
        val width = image.width
        val height = image.height
        val pixels =
            IntArray(width * height).also { image.getPixels(it, 0, width, 0, 0, width, height) }
        val lightPixels = pixels.map { color ->
            Color.argb(
                Color.TRANSPARENT,
                Color.red(color) * this.ALPHA,
                Color.green(color) * this.ALPHA,
                Color.blue(color) * this.ALPHA
            )
        }.toIntArray()
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            .apply { setPixels(lightPixels, 0, width, 0, 0, width, height) }
    }

    val baseImage = aircraft.image
    val lightImage = makeLightImage(this.baseImage)
    override var image = this.baseImage

    var life = this.LIVE_MS
    override fun forward(timeMs: Int) {
        this.life -= timeMs
        if (this.life <= 0) {
            this.vanish()
        }
        this.image = if ((this.life / FLASH_MS) % 2 == 1) {
            this.baseImage
        } else {
            this.lightImage
        }
    }
}