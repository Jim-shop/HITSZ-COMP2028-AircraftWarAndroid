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

    val alpha = 0x80
    val liveMs = 500
    val flashMs = 100

    fun makeLightImage(image: Bitmap): Bitmap {
        return image.run {
            IntArray(width * height).also {
                getPixels(it, 0, width, 0, 0, width, height)
            }.map { color ->
                Color.argb(
                    this@DyingAnimation.alpha,
                    Color.red(color),
                    Color.green(color),
                    Color.blue(color)
                )
            }.toIntArray().run {
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    .also { setPixels(this, 0, width, 0, 0, width, height) }
            }
        }
    }

    val baseImage = aircraft.image
    val lightImage = makeLightImage(this.baseImage)
    override var image = this.baseImage

    var life = this.liveMs
    override fun forward(timeMs: Int) {
        this.life -= timeMs
        if (this.life <= 0) {
            this.vanish()
        }
        this.image = if ((this.life / flashMs) % 2 == 1) {
            this.baseImage
        } else {
            this.lightImage
        }
    }
}