package net.imshit.aircraftwar.element.aircraft.enemy

import net.imshit.aircraftwar.element.event.GameEvents
import net.imshit.aircraftwar.element.generate.prop.PropGenerateStrategies
import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.logic.game.Games

class BossEnemy(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    maxHp: Int,
    power: Int,
    shootNum: Int,
    private val propGenerateStrategy: PropGenerateStrategies
) : Enemies(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = 0f,
    maxHp = maxHp,
    power = power,
    shootNum = shootNum
) {
    override val image = game.images.aircraftBoss

    override fun forward(timeMs: Int) {
        this.x += this.speedX * timeMs
        if (this.x < 0 || this.x >= game.width) {
            this.speedX = -speedX
        }
    }

    override fun prop(): List<Props> {
        return listOf(
            propGenerateStrategy.createPropNotNull(this.x - 20, this.y - 10),
            propGenerateStrategy.createPropNotNull(this.x, this.y),
            propGenerateStrategy.createPropNotNull(this.x + 20, this.y - 10)
        )
    }

    override val credits = 200

    override fun notify(e: GameEvents) {
        when (e) {
            GameEvents.BOMB_ACTIVATE -> this.hp /= 2
        }
    }
}