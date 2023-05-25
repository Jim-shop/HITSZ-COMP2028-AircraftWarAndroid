package net.imshit.aircraftwar.element.generate.prop

import net.imshit.aircraftwar.logic.game.Games

class MediumPropGenerateStrategy(game: Games) : PropGenerateStrategies(game = game) {
    override val bloodProbability = 0.2
    override val bombProbability = 0.2
    override val bulletProbability = 0.2
}