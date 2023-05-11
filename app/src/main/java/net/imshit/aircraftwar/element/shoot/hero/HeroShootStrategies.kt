package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.bullet.HeroBullet
import net.imshit.aircraftwar.element.shoot.ShootStrategies
import net.imshit.aircraftwar.logic.game.Games

sealed class HeroShootStrategies(game: Games) : ShootStrategies(game = game) {
    abstract override fun shoot(x: Float, y: Float, speedY: Float, power: Int): List<HeroBullet>
}