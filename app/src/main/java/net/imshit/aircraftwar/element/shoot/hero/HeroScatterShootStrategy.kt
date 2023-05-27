package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.logic.game.Games
import kotlin.math.absoluteValue

class HeroScatterShootStrategy(game: Games) : HeroShootStrategies(game = game) {
    override fun shoot(
        x: Float, y: Float, speedY: Float, power: Int
    ): List<HeroBullet> {
        val direction = -1
        val shootNum = 3
        val bulletCenterY = y + direction * 2
        val bulletCenterSpeedX = 0f
        val bulletCenterSpeedY = speedY + direction * 0.2f
        return mutableListOf<HeroBullet>().apply {
            for (i in 0 until shootNum) {
                add(
                    HeroBullet(
                        this@HeroScatterShootStrategy.game,
                        x + (i * 2 - shootNum + 1) * 20,
                        bulletCenterY + (i - shootNum / 2f).absoluteValue * 20,
                        bulletCenterSpeedX + (i * 2 - shootNum + 1) * 0.05f,
                        bulletCenterSpeedY,
                        power
                    )
                )
            }
        }.toList()
    }
}