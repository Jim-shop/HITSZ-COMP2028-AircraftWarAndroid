package net.imshit.aircraftwar.element.shoot.enemy

import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.logic.Games

class EnemyNoShootStrategy : EnemyShootStrategy {
    override fun shoot(
        game: Games, x: Float, y: Float, speedY: Float, power: Int
    ): List<EnemyBullet> {
        return listOf()
    }
}