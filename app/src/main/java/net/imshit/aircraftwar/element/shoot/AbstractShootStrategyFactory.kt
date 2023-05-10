package net.imshit.aircraftwar.element.shoot

abstract class AbstractShootStrategyFactory {
    abstract fun getStrategy(shootNum: Int): ShootStrategy
}
