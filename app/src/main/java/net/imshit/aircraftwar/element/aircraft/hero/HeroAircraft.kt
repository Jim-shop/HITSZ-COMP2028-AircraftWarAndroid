package net.imshit.aircraftwar.element.aircraft.hero

import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.element.AbstractShootStrategyFactory
import net.imshit.aircraftwar.element.aircraft.AbstractAircraft

object HeroAircraft : AbstractAircraft(
    game = TODO(),
    initialLocationX = 0f,
    initialLocationY = 0f,
    speedX = 0f,
    speedY = 0f,
    hp = 1000,
    power = 30,
//    strategyFactory = HeroShootStrategyFactory(),
    strategyFactory = AbstractShootStrategyFactory(),
    shootNum = 1
) {
    override val image = R.drawable.game_aircraft_hero
    override val width: Int
        get() = TODO("Not yet implemented")
    override var height: Int
        get() = TODO("Not yet implemented")
        set(_) {}
}