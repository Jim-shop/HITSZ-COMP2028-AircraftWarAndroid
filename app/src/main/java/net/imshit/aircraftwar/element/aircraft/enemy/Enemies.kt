package net.imshit.aircraftwar.element.aircraft.enemy

import net.imshit.aircraftwar.element.aircraft.AbstractAircraft
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.element.shoot.enemy.EnemyShootStrategyFactory
import net.imshit.aircraftwar.logic.EnemyListener
import net.imshit.aircraftwar.logic.GameEvents
import net.imshit.aircraftwar.logic.Games

sealed class Enemies(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    maxHp: Int,
    power: Int,
    shootNum: Int
) : AbstractAircraft(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    maxHp = maxHp,
    power = power,
    shootStrategyFactory = EnemyShootStrategyFactory(game),
    shootNum = shootNum
), EnemyListener {
    open val credits: Int = 0

    open fun prop(): List<Props> {
        return listOf()
    }

    override fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> vanish()
        }
    }
}
