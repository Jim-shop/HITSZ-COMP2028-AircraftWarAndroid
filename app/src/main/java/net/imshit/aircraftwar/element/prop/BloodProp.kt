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

package net.imshit.aircraftwar.element.prop

import net.imshit.aircraftwar.logic.game.Games

class BloodProp(
    game: Games, initialX: Float, initialY: Float, speedX: Float, speedY: Float
) : Props(
    game = game, initialX = initialX, initialY = initialY, speedX = speedX, speedY = speedY
) {
    override val image = game.images.propBlood
    override fun activate() {
        game.heroAircraft.increaseHp(100)
    }
}