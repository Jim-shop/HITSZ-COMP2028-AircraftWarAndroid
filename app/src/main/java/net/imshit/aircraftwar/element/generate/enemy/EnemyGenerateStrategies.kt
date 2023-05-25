package net.imshit.aircraftwar.element.generate.enemy

import net.imshit.aircraftwar.element.aircraft.enemy.BossEnemy
import net.imshit.aircraftwar.element.aircraft.enemy.Enemies
import net.imshit.aircraftwar.element.aircraft.enemy.factory.BossEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.EliteEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.MobEnemyFactory

sealed class EnemyGenerateStrategies {
    abstract val mobEnemyFactory: MobEnemyFactory
    abstract val eliteEnemyFactory: EliteEnemyFactory
    abstract val bossEnemyFactory: BossEnemyFactory

    abstract val mobProbability: Double
    abstract val enemyMaxNumber: Int
    abstract val bossScoreThreshold: Int
    abstract val enemySummonInterval: Int
    abstract val enemyShootInterval: Int
    abstract val heroShootInterval: Int

    abstract val hpIncreaseRate: Double
    abstract val powerIncreaseRate: Double
    abstract val speedIncreaseRate: Double
    abstract val bossHpIncreaseRate: Double

    abstract val mobBaseHp: Int
    abstract val eliteBaseHp: Int
    abstract val bossBaseHp: Int

    abstract val eliteBasePower: Int
    abstract val bossBasePower: Int

    abstract val mobBaseSpeed: Float
    abstract val eliteBaseSpeed: Float
    abstract val bossBaseSpeed: Float


    private var time = 0
    private var score = 0

    private var lastBossSummonScore = 0
    private var lastEnemySummonTime = 0
    private var lastEnemyShootTime = 0
    private var lastHeroShootTime = 0

    fun inform(time: Int, score: Int) {
        this.time = time
        this.score = score
    }

    fun generateEnemy(currentEnemyNum: Int): List<Enemies> {
        return if (currentEnemyNum < this.enemyMaxNumber) {
            if (Math.random() < this.mobProbability) {
                val hp = (this.mobBaseHp + this.score * this.hpIncreaseRate).toInt()
                val speed = (this.mobBaseSpeed + this.score * this.speedIncreaseRate).toFloat()
                listOf(this.mobEnemyFactory.createEnemy(hp, 0, speed))
            } else {
                val hp = (this.eliteBaseHp + this.score * this.hpIncreaseRate).toInt()
                val power = (this.eliteBasePower + this.score * this.powerIncreaseRate).toInt()
                val speed = (this.eliteBaseSpeed + this.score * this.speedIncreaseRate).toFloat()
                listOf(this.eliteEnemyFactory.createEnemy(hp, power, speed))
            }
        } else {
            listOf()
        }
    }

    open fun generateBoss(): BossEnemy? {
        val hp = (this.bossBaseHp + this.lastBossSummonScore * this.bossHpIncreaseRate).toInt()
        val power = (this.bossBasePower + this.score * powerIncreaseRate).toInt()
        val speed = (this.bossBaseSpeed + this.score * speedIncreaseRate).toFloat()
        return this.bossEnemyFactory.createEnemy(hp, power, speed)
    }

    open fun isTimeToGenerateBoss(currentBoss: BossEnemy?): Boolean =
        if (currentBoss == null && this.score - this.lastBossSummonScore > this.bossScoreThreshold) {
            this.lastBossSummonScore = this.score
            true
        } else {
            false
        }

    fun isTimeToGenerateEnemy(): Boolean =
        if (this.time - this.lastEnemySummonTime > this.enemySummonInterval) {
            this.lastEnemySummonTime = this.time
            true
        } else {
            false
        }

    fun isTimeForEnemyShoot(): Boolean =
        if (this.time - this.lastEnemyShootTime > this.enemyShootInterval) {
            this.lastEnemyShootTime = this.time
            true
        } else {
            false
        }

    fun isTimeForHeroShoot(): Boolean =
        if (this.time - this.lastHeroShootTime > this.heroShootInterval) {
            this.lastHeroShootTime = this.time
            true
        } else {
            false
        }
}