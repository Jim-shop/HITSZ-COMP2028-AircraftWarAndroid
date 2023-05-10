package net.imshit.aircraftwar.element.bullet

import net.imshit.aircraftwar.logic.Games
import net.imshit.aircraftwar.util.listener.EnemyListener
import net.imshit.aircraftwar.util.listener.GameEvents

class EnemyBullet(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float, power: Int
) : AbstractBullet(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    power = power
), EnemyListener {
    override val image = game.images.bulletEnemy
    override fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> this.vanish()
        }
    }
}