package net.imshit.aircraftwar.element.aircraft.enemy.factory

import net.imshit.aircraftwar.element.aircraft.enemy.EliteEnemy
import net.imshit.aircraftwar.element.generate.prop.PropGenerateStrategies
import net.imshit.aircraftwar.logic.game.Games

class EliteEnemyFactory(game: Games, private val propGenerateStrategy: PropGenerateStrategies) :
    EnemyFactories(game = game) {

    override fun createEnemy(hp: Int, power: Int, speed: Float): EliteEnemy {
        val x = (Math.random() * (game.width - game.images.aircraftElite.width)).toFloat()
        return EliteEnemy(game, x, 0f, 0f, speed, hp, power, this.propGenerateStrategy)
    }
}