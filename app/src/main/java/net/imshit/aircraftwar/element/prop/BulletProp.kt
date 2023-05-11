package net.imshit.aircraftwar.element.prop

import net.imshit.aircraftwar.logic.game.Games
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.atomic.AtomicInteger

class BulletProp(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float
) : Props(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    companion object {
        private val usedCount = AtomicInteger(0)
    }

    override val image = game.images.propBullet

    override fun activate() {
        usedCount.getAndIncrement()
        val heroAircraft = game.heroAircraft
        heroAircraft.setShootNum(3)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (usedCount.decrementAndGet() <= 0) {
                    heroAircraft.setShootNum(1)
                }
            }
        }, 10000)
    }
}