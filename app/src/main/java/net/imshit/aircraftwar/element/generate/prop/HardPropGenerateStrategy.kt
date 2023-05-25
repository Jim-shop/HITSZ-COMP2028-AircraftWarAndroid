package net.imshit.aircraftwar.element.generate.prop

import net.imshit.aircraftwar.logic.game.Games

class HardPropGenerateStrategy(game: Games) : PropGenerateStrategies(game = game) {
    override val bloodProbability = 0.1
    override val bombProbability = 0.1
    override val bulletProbability = 0.1
}