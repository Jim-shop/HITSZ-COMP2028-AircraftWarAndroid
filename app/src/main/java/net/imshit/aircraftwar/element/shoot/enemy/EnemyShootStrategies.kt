package net.imshit.aircraftwar.element.shoot.enemy

import net.imshit.aircraftwar.element.bullet.EnemyBullet
import net.imshit.aircraftwar.element.shoot.ShootStrategies
import net.imshit.aircraftwar.logic.game.Games

sealed class EnemyShootStrategies(game: Games) : ShootStrategies(game = game) {
    abstract override fun shoot(x: Float, y: Float, speedY: Float, power: Int): List<EnemyBullet>
}