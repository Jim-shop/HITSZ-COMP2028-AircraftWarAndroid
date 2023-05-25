package net.imshit.aircraftwar.element.generate.prop

import net.imshit.aircraftwar.logic.game.Games

class EasyPropGenerateStrategy(game: Games) : PropGenerateStrategies(game = game) {
    override val bloodProbability = 0.3
    override val bombProbability = 0.3
    override val bulletProbability = 0.3
}