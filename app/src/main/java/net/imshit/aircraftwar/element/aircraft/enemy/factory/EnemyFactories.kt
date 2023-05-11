package net.imshit.aircraftwar.element.aircraft.enemy.factory

import net.imshit.aircraftwar.element.aircraft.enemy.Enemies
import net.imshit.aircraftwar.logic.game.Games

sealed class EnemyFactories(val game: Games) {
    abstract fun createEnemy(hp: Int, power: Int, speed: Float): Enemies
}