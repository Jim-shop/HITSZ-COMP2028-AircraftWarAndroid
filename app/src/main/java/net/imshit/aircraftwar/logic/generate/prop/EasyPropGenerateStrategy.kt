package net.imshit.aircraftwar.logic.generate.prop

import net.imshit.aircraftwar.logic.Games

class EasyPropGenerateStrategy(game: Games) : PropGenerateStrategies(game = game) {
    override val bloodProbability = 0.3
    override val bombProbability = 0.3
    override val bulletProbability = 0.3
}