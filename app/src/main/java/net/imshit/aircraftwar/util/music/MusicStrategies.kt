package net.imshit.aircraftwar.util.music

import android.content.Context

sealed class MusicStrategies(context: Context) {
    abstract fun setBgm(bgmType: BgmType)
    abstract fun playBulletHit()
    abstract fun playBulletShoot()
    abstract fun playExplode()
    abstract fun playGameOver()
    abstract fun playSupplyGet()
}
