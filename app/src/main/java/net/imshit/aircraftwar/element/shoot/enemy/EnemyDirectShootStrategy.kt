package net.imshit.aircraftwar.element.shoot.enemy

import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.logic.Games

class EnemyDirectShootStrategy(game: Games) : EnemyShootStrategies(game = game) {
    override fun shoot(x: Float, y: Float, speedY: Float, power: Int): List<EnemyBullet> {
        val direction = 1
        val bulletY = y + direction * 2
        val bulletSpeedY = speedY + direction * 0.1f
        return listOf(EnemyBullet(this.game, x, bulletY, 0f, bulletSpeedY, power))
    }
}