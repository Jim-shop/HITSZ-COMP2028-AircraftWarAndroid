package net.imshit.aircraftwar.element.shoot

import net.imshit.aircraftwar.element.bullet.Bullets
import net.imshit.aircraftwar.logic.game.Games

abstract class ShootStrategies(val game: Games) {
    abstract fun shoot(x: Float, y: Float, speedY: Float, power: Int): List<Bullets>
}