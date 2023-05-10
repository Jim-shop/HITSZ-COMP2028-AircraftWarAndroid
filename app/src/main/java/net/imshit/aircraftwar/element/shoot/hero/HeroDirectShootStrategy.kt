package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.logic.Games

class HeroDirectShootStrategy : HeroShootStrategy {
    override fun shoot(
        game: Games, x: Float, y: Float, speedY: Float, power: Int
    ): List<HeroBullet> {
        val direction = -1
        val bulletY = y + direction * 2
        val bulletSpeedY = speedY + direction * 0.2f
        return listOf(HeroBullet(game, x, bulletY, 0f, bulletSpeedY, power))
    }
}