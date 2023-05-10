package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.shoot.AbstractShootStrategyFactory

class HeroShootStrategyFactory : AbstractShootStrategyFactory() {
    override fun getStrategy(shootNum: Int): HeroShootStrategy {
        return when (shootNum) {
            1 -> HeroDirectShootStrategy()
            3 -> HeroScatterShootStrategy()
            else -> HeroDirectShootStrategy()
        }
    }
}