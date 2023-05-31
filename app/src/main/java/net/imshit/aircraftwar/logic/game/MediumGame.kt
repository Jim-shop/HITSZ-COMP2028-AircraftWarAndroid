package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import net.imshit.aircraftwar.element.generate.enemy.MediumEnemyGenerateStrategy

class MediumGame(
    context: Context,
    attrs: AttributeSet?,
    soundMode: Boolean,
    onlineMode: Boolean,
    roomId: Int,
    handler: Handler
) :
    Games(
        context = context,
        attrs = attrs,
        soundMode = soundMode,
        onlineMode = onlineMode,
        roomId = roomId,
        handler = handler
    ) {

    override val generateStrategy = MediumEnemyGenerateStrategy(this)

    override fun init() {
        super.init()
        this.background = images.backgroundMedium
    }
}