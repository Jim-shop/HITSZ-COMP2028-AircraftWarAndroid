package net.imshit.aircraftwar.element.aircraft.enemy

import net.imshit.aircraftwar.logic.game.Games

class MobEnemy(
    game: Games,
    initialX: Float,
    initialY: Float,
    speedX: Float,
    speedY: Float,
    maxHp: Int,
) : Enemies(
    game = game,
    initialX = initialX,
    initialY = initialY,
    speedX = speedX,
    speedY = speedY,
    maxHp = maxHp,
    power = 0,
    shootNum = 0
) {
    override val image = game.images.aircraftMob

    override val credits = 30
}