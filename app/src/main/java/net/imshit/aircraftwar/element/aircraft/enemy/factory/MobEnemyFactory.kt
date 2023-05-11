package net.imshit.aircraftwar.element.aircraft.enemy.factory

import net.imshit.aircraftwar.element.aircraft.enemy.MobEnemy
import net.imshit.aircraftwar.logic.game.Games

class MobEnemyFactory(game: Games) : EnemyFactories(game = game) {

    override fun createEnemy(hp: Int, power: Int, speed: Float): MobEnemy {
        val x = (Math.random() * (game.width - game.images.aircraftMob.width)).toFloat()
        return MobEnemy(game, x, 0f, 0f, speed, hp)
    }
}