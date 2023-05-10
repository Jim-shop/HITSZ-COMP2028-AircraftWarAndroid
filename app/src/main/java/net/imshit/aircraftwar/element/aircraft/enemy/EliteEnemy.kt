package net.imshit.aircraftwar.element.aircraft.enemy

import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.Games
import net.imshit.aircraftwar.logic.generate.prop.PropGenerateStrategies

class EliteEnemy(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    maxHp: Int,
    power: Int,
    shootNum: Int,
    val propGenerateStrategy: PropGenerateStrategies
) : Enemies(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    maxHp = maxHp,
    power = power,
    shootNum = shootNum
) {
    override val image = game.images.aircraftElite

    override fun prop(): List<Props> {
        return propGenerateStrategy.createProp(this.x, this.y)?.let { listOf(it) } ?: listOf()
    }

    override val credits = 60
}