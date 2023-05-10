package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.shoot.ShootStrategyFactories
import net.imshit.aircraftwar.logic.Games

class HeroShootStrategyFactory(game: Games) : ShootStrategyFactories(game = game) {
    override fun getStrategy(shootNum: Int): HeroShootStrategies {
        return when (shootNum) {
            1 -> HeroDirectShootStrategy(this.game)
            3 -> HeroScatterShootStrategy(this.game)
            else -> HeroDirectShootStrategy(this.game)
        }
    }
}