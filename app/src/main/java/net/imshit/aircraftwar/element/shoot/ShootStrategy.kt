package net.imshit.aircraftwar.element.shoot

import net.imshit.aircraftwar.element.bullet.Bullets
import net.imshit.aircraftwar.logic.Games

interface ShootStrategy {
    fun shoot(game: Games, x: Float, y: Float, speedY: Float, power: Int): List<Bullets>
}