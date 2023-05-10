package net.imshit.aircraftwar.element.aircraft.enemy.factory

import net.imshit.aircraftwar.element.aircraft.enemy.BossEnemy
import net.imshit.aircraftwar.logic.Games
import net.imshit.aircraftwar.logic.generate.prop.PropGenerateStrategies

class BossEnemyFactory(game: Games, private val propGenerateStrategy: PropGenerateStrategies) :
    EnemyFactories(game = game) {

    override fun createEnemy(hp: Int, power: Int, speed: Float): BossEnemy {
        val x = (Math.random() * (game.screenWidth - game.images.aircraftBoss.width)).toFloat()
        val speedX = speed * (if (Math.random() < 0.5) -1 else 1)
        return BossEnemy(game, x, 0f, speedX, hp, power, 3, this.propGenerateStrategy)
    }
}