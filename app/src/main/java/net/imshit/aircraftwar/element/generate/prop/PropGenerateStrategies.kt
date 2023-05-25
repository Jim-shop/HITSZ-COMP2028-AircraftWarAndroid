package net.imshit.aircraftwar.element.generate.prop

import net.imshit.aircraftwar.element.prop.Props
import net.imshit.aircraftwar.element.prop.factory.BloodPropFactory
import net.imshit.aircraftwar.element.prop.factory.BombPropFactory
import net.imshit.aircraftwar.element.prop.factory.BulletPropFactory
import net.imshit.aircraftwar.element.prop.factory.PropFactories
import net.imshit.aircraftwar.logic.game.Games
import java.lang.Math.random

abstract class PropGenerateStrategies(game: Games) : PropFactories(game = game) {
    protected abstract val bloodProbability: Double
    protected abstract val bombProbability: Double
    protected abstract val bulletProbability: Double

    private val bloodPropFactory = BloodPropFactory(game)
    private val bombPropFactory = BombPropFactory(game)
    private val bulletPropFactory = BulletPropFactory(game)

    override fun createProp(x: Float, y: Float): Props? {
        val rand = random()
        return if (rand < bloodProbability) {
            bloodPropFactory.createProp(x, y)
        } else if (rand < bloodProbability + bombProbability) {
            bombPropFactory.createProp(x, y)
        } else if (rand < bloodProbability + bombProbability + bulletProbability) {
            bulletPropFactory.createProp(x, y)
        } else {
            null
        }
    }

    fun createPropNotNull(x: Float, y: Float): Props {
        val rand = random() / (bloodProbability + bombProbability + bulletProbability)
        return if (rand < bloodProbability) {
            bloodPropFactory.createProp(x, y)
        } else if (rand < bloodProbability + bombProbability) {
            bombPropFactory.createProp(x, y)
        } else {
            bulletPropFactory.createProp(x, y)
        }
    }
}