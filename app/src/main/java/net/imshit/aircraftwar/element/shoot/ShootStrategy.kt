package net.imshit.aircraftwar.element.shoot

import net.imshit.aircraftwar.element.bullet.AbstractBullet

interface ShootStrategy {
    fun shoot(x: Float, y: Float, speedY: Float, power: Int): List<AbstractBullet>
}