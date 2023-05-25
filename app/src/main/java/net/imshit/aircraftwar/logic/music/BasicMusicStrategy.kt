package net.imshit.aircraftwar.logic.music

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import net.imshit.aircraftwar.data.resource.MusicManager

class BasicMusicStrategy(context: Context) : MusicStrategies() {

    private val audioManager = MusicManager(context)
    private val bgmPlayer = MediaPlayer()
    private val soundPool = SoundPool.Builder().setMaxStreams(100).setAudioAttributes(
        AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
    ).build()

    private enum class Sound {
        BULLET_HIT, BULLET_SHOOT, EXPLODE, GAME_OVER, SUPPLY_GET
    }

    private val soundIds = mapOf(
        Sound.BULLET_HIT to this.soundPool.load(this.audioManager.bulletHit, 0),
        Sound.BULLET_SHOOT to this.soundPool.load(this.audioManager.bulletShoot, 0),
        Sound.EXPLODE to this.soundPool.load(this.audioManager.explode, 0),
        Sound.GAME_OVER to this.soundPool.load(this.audioManager.gameOver, 0),
        Sound.SUPPLY_GET to this.soundPool.load(this.audioManager.supplyGet, 0),
    )

    override fun setBgm(bgmType: BgmType) {
        with(this.bgmPlayer) {
            reset()
            when (bgmType) {
                BgmType.NONE -> {}
                BgmType.NORMAL -> {
                    setDataSource(audioManager.bgmNormal)
                    prepare()
                    start()
                }

                BgmType.BOSS -> {
                    setDataSource(audioManager.bgmBoss)
                    prepare()
                    start()
                }
            }
        }
    }

    private fun play(sound: Sound) {
        this.soundPool.play(soundIds[sound]!!, 1f, 1f, 0, 0, 1f)
    }

    override fun playBulletHit() {
        play(Sound.BULLET_HIT)
    }

    override fun playBulletShoot() {
//        play(Sound.BULLET_SHOOT)
    }

    override fun playExplode() {
        play(Sound.EXPLODE)
    }

    override fun playSupplyGet() {
        play(Sound.SUPPLY_GET)
    }

    override fun release() {
        bgmPlayer.release()
        soundPool.release()
    }

    override fun resume() {
        bgmPlayer.start()
        soundPool.autoResume()
    }

    override fun pause() {
        bgmPlayer.pause()
        soundPool.autoPause()
    }

    override fun playGameOver() {
        this.soundPool.autoPause()
        play(Sound.GAME_OVER)
    }
}