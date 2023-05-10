package net.imshit.aircraftwar.logic.generate.prop

import net.imshit.aircraftwar.logic.Games

class MediumPropGenerateStrategy(game: Games) : PropGenerateStrategies(game = game) {
    override val bloodProbability = 0.2
    override val bombProbability = 0.2
    override val bulletProbability = 0.2
}