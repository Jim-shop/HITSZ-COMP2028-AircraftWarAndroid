package net.imshit.aircraftwar.util.resource

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