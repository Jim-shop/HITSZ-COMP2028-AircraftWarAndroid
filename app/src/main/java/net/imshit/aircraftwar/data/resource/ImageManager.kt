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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

class ImageManager(context: Context, width: Int) {
    private val scaleMatrix = (width / 512f).let { Matrix().apply { postScale(it, it) } }
    private val readImage: (String) -> Bitmap = { fileName ->
        BitmapFactory.decodeStream(context.assets.open("img/$fileName")).run {
            Bitmap.createBitmap(this, 0, 0, this.width, this.height, scaleMatrix, true)
        }
    }

    val backgroundEasy = readImage("game_bg_easy.webp")
    val backgroundMedium = readImage("game_bg_medium.webp")
    val backgroundHard = readImage("game_bg_hard.webp")
    val aircraftHero = readImage("game_aircraft_hero.png")
    val aircraftMob = readImage("game_aircraft_mob.png")
    val aircraftElite = readImage("game_aircraft_elite.png")
    val aircraftBoss = readImage("game_aircraft_boss.png")
    val bulletHero = readImage("game_bullet_hero.png")
    val bulletEnemy = readImage("game_bullet_enemy.png")
    val propBlood = readImage("game_prop_blood.png")
    val propBomb = readImage("game_prop_bomb.png")
    val propBullet = readImage("game_prop_bullet.png")
}