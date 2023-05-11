package net.imshit.aircraftwar.element.shoot

import net.imshit.aircraftwar.logic.game.Games

abstract class ShootStrategyFactories(val game: Games) {
    abstract fun getStrategy(shootNum: Int): ShootStrategies
}
