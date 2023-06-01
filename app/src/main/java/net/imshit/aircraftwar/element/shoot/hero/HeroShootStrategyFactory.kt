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

package net.imshit.aircraftwar.element.shoot.hero

import net.imshit.aircraftwar.element.shoot.ShootStrategyFactories
import net.imshit.aircraftwar.logic.game.Games

class HeroShootStrategyFactory(game: Games) : ShootStrategyFactories(game = game) {
    override fun getStrategy(shootNum: Int): HeroShootStrategies {
        return when (shootNum) {
            1 -> HeroDirectShootStrategy(this.game)
            3 -> HeroScatterShootStrategy(this.game)
            else -> HeroDirectShootStrategy(this.game)
        }
    }
}