package net.imshit.aircraftwar.logic.generate.enemy

import net.imshit.aircraftwar.element.aircraft.enemy.BossEnemy
import net.imshit.aircraftwar.element.aircraft.enemy.factory.BossEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.EliteEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.MobEnemyFactory
import net.imshit.aircraftwar.logic.game.Games
import net.imshit.aircraftwar.logic.generate.prop.EasyPropGenerateStrategy

class EasyEnemyGenerateStrategy(game: Games) : EnemyGenerateStrategies() {
    private val propStrategy = EasyPropGenerateStrategy(game)
    override val mobEnemyFactory = MobEnemyFactory(game)
    override val eliteEnemyFactory = EliteEnemyFactory(game, propStrategy)
    override val bossEnemyFactory = BossEnemyFactory(game, propStrategy)
    override val mobProbability = 0.8
    override val enemyMaxNumber = 5
    override val bossScoreThreshold = 0
    override val enemySummonInterval = 800
    override val enemyShootInterval = 500
    override val heroShootInterval = 100
    override val hpIncreaseRate = 0.0
    override var powerIncreaseRate = 0.0
    override var speedIncreaseRate = 0.0
    override var bossHpIncreaseRate = 0.0
    override var mobBaseHp = 30
    override var eliteBaseHp = 60
    override var bossBaseHp = 0
    override var eliteBasePower = 60
    override var bossBasePower = 0
    override var mobBaseSpeed = 0.15f
    override var eliteBaseSpeed = 0.10f
    override var bossBaseSpeed = 0f

    override fun generateBoss(): BossEnemy? {
        return null
    }

    override fun isTimeToGenerateBoss(currentBoss: BossEnemy?) = false
}