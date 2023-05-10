package net.imshit.aircraftwar.element.shoot.enemy

import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.logic.Games

class EnemyNoShootStrategy(game: Games) : EnemyShootStrategies(game = game) {
    override fun shoot(x: Float, y: Float, speedY: Float, power: Int): List<EnemyBullet> {
        return listOf()
    }
}