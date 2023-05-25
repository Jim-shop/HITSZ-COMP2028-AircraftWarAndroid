package net.imshit.aircraftwar.element.aircraft.enemy

import net.imshit.aircraftwar.element.generate.prop.PropGenerateStrategies
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.game.Games

class EliteEnemy(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    maxHp: Int,
    power: Int,
    private val propGenerateStrategy: PropGenerateStrategies
) : Enemies(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    maxHp = maxHp,
    power = power,
    shootNum = 1
) {
    override val image = game.images.aircraftElite

    override fun prop(): List<Props> {
        return propGenerateStrategy.createProp(this.x, this.y)?.let { listOf(it) } ?: listOf()
    }

    override val credits = 60
}