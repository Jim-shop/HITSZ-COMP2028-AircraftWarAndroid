package net.imshit.aircraftwar.element.aircraft.hero

import android.content.res.Resources
import android.graphics.BitmapFactory
import net.imshit.aircraftwar.R
import net.imshit.aircraftwar.element.AbstractShootStrategyFactory
import net.imshit.aircraftwar.element.aircraft.AbstractAircraft

object HeroAircraft : AbstractAircraft(
    locationX = 0f,
    locationY = 0f,
    speedX = 0f,
    speedY = 0f,
    hp = 1000,
    power = 30,
//    strategyFactory = HeroShootStrategyFactory(),
    strategyFactory = AbstractShootStrategyFactory(),
    shootNum = 1
) {
    override val image = BitmapFactory.decodeResource(Resources.getSystem() ,R.drawable.aircraft_hero)
}