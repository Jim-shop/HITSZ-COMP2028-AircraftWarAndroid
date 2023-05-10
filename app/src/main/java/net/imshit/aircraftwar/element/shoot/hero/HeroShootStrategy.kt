package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.shoot.ShootStrategy
import net.imshit.aircraftwar.logic.Games

interface HeroShootStrategy : ShootStrategy {
    override fun shoot(game: Games, x: Float, y: Float, speedY: Float, power: Int): List<HeroBullet>
}