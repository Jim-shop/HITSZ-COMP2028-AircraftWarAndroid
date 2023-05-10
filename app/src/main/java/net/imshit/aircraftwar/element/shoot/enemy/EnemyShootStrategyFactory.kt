package net.imshit.aircraftwar.element.shoot.enemy

import net.imshit.aircraftwar.element.shoot.AbstractShootStrategyFactory

class EnemyShootStrategyFactory : AbstractShootStrategyFactory() {
    override fun getStrategy(shootNum: Int): EnemyShootStrategy {
        return when (shootNum) {
            0 -> EnemyNoShootStrategy()
            1 -> EnemyDirectShootStrategy()
            3 -> EnemyScatterShootStrategy()
            else -> EnemyNoShootStrategy()
        }
    }
}