package net.imshit.aircraftwar.element.shoot.enemy

import net.imshit.aircraftwar.element.shoot.ShootStrategyFactories
import net.imshit.aircraftwar.logic.Games

class EnemyShootStrategyFactory(game: Games) : ShootStrategyFactories(game = game) {
    override fun getStrategy(shootNum: Int): EnemyShootStrategies {
        return when (shootNum) {
            0 -> EnemyNoShootStrategy(this.game)
            1 -> EnemyDirectShootStrategy(this.game)
            3 -> EnemyScatterShootStrategy(this.game)
            else -> EnemyNoShootStrategy(this.game)
        }
    }
}