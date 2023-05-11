package net.imshit.aircraftwar.logic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

class ImageManager(context: Context, width: Int) {
    val scaleMatrix = (width / 512f).let { Matrix().apply { postScale(it, it) } }
    val readImage: (String) -> Bitmap = { fileName ->
        BitmapFactory.decodeStream(context.assets.open("img/$fileName")).run {
            Bitmap.createBitmap(this, 0, 0, this.width, this.height, scaleMatrix, true)
        }
    }

    val backgroundEasy: Bitmap = readImage("game_bg_easy.webp")
    val backgroundMedium: Bitmap = readImage("game_bg_medium.webp")
    val backgroundHard: Bitmap = readImage("game_bg_hard.webp")
    val aircraftHero: Bitmap = readImage("game_aircraft_hero.png")
    val aircraftMob: Bitmap = readImage("game_aircraft_mob.png")
    val aircraftElite: Bitmap = readImage("game_aircraft_elite.png")
    val aircraftBoss: Bitmap = readImage("game_aircraft_boss.png")
    val bulletHero: Bitmap = readImage("game_bullet_hero.png")
    val bulletEnemy: Bitmap = readImage("game_bullet_enemy.png")
    val propBlood: Bitmap = readImage("game_prop_blood.png")
    val propBomb: Bitmap = readImage("game_prop_bomb.png")
    val propBullet: Bitmap = readImage("game_prop_bullet.png")
}