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

package net.imshit.aircraftwar.data.resource

import android.content.Context
import android.content.res.AssetFileDescriptor

class MusicManager(context: Context) {
    private val getAfd: (String) -> AssetFileDescriptor = { fileName ->
        context.assets.openFd("audio/$fileName")
    }

    val bgmBoss = getAfd("game_bgm_boss.wav")
    val bgmNormal = getAfd("game_bgm_normal.wav")
    val bulletHit = getAfd("game_bullet_hit.wav")
    val bulletShoot = getAfd("game_bullet_shoot.wav")
    val explode = getAfd("game_explode.wav")
    val gameOver = getAfd("game_game_over.wav")
    val supplyGet = getAfd("game_supply_get.wav")
}