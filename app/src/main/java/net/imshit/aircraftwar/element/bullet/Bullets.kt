package net.imshit.aircraftwar.element.bullet

import net.imshit.aircraftwar.element.AbstractFlyingObject
import net.imshit.aircraftwar.logic.game.Games

sealed class Bullets(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float, val power: Int
) : AbstractFlyingObject(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    override fun forward(timeMs: Int) {
        super.forward(timeMs)
        // 额外判定 y 轴出界
        if (this.y <= 0) {
            vanish()
        }
    }
}