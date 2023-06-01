/*
 * Copyright (c) [2023] [Jim-shop]
 * [AircraftwarAndroid] is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */

package net.imshit.aircraftwar.element.generate.enemy

import net.imshit.aircraftwar.element.aircraft.enemy.factory.BossEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.EliteEnemyFactory
import net.imshit.aircraftwar.element.aircraft.enemy.factory.MobEnemyFactory
import net.imshit.aircraftwar.element.generate.prop.MediumPropGenerateStrategy
import net.imshit.aircraftwar.logic.game.Games

class MediumEnemyGenerateStrategy(game: Games) : EnemyGenerateStrategies() {
    private val propStrategy = MediumPropGenerateStrategy(game)
    override val mobEnemyFactory = MobEnemyFactory(game)
    override val eliteEnemyFactory = EliteEnemyFactory(game, propStrategy)
    override val bossEnemyFactory = BossEnemyFactory(game, propStrategy)
    override val mobProbability = 0.7
    override val enemyMaxNumber = 8
    override val bossScoreThreshold = 100
    override val enemySummonInterval = 600
    override val enemyShootInterval = 500
    override val heroShootInterval = 200
    override val hpIncreaseRate = 0.05
    override var powerIncreaseRate = 0.05
    override var speedIncreaseRate = 0.0005
    override var bossHpIncreaseRate = 0.0
    override var mobBaseHp = 45
    override var eliteBaseHp = 75
    override var bossBaseHp = 200
    override var eliteBasePower = 30
    override var bossBasePower = 30
    override var mobBaseSpeed = 0.15f
    override var eliteBaseSpeed = 0.10f
    override var bossBaseSpeed = 0.05f
}