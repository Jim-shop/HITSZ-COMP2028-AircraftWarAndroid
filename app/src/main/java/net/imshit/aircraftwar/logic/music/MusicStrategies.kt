package net.imshit.aircraftwar.logic.music

sealed class MusicStrategies {
    abstract fun setBgm(bgmType: BgmType)

    abstract fun playBulletHit()
    abstract fun playBulletShoot()
    abstract fun playExplode()
    abstract fun playGameOver()
    abstract fun playSupplyGet()

    abstract fun release()
}
