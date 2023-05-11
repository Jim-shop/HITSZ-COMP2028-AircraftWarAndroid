package net.imshit.aircraftwar.logic.generate.enemy

import net.imshit.aircraftwar.element.aircraft.enemy.factory.BossEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.EliteEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.MobEnemyFactory
import net.imshit.aircraftwar.logic.game.Games
import net.imshit.aircraftwar.logic.generate.prop.HardPropGenerateStrategy

class HardEnemyGenerateStrategy(game: Games) : EnemyGenerateStrategies() {
    private val propStrategy = HardPropGenerateStrategy(game)
    override val mobEnemyFactory = MobEnemyFactory(game)
    override val eliteEnemyFactory = EliteEnemyFactory(game, propStrategy)
    override val bossEnemyFactory = BossEnemyFactory(game, propStrategy)
    override val mobProbability = 0.6
    override val enemyMaxNumber = 10
    override val bossScoreThreshold = 5000
    override val enemySummonInterval = 500
    override val enemyShootInterval = 500
    override val heroShootInterval = 500
    override val hpIncreaseRate = 0.08
    override var powerIncreaseRate = 0.08
    override var speedIncreaseRate = 0.0008
    override var bossHpIncreaseRate = 0.08
    override var mobBaseHp = 60
    override var eliteBaseHp = 120
    override var bossBaseHp = 500
    override var eliteBasePower = 60
    override var bossBasePower = 60
    override var mobBaseSpeed = 0.20f
    override var eliteBaseSpeed = 0.15f
    override var bossBaseSpeed = 0.10f
}