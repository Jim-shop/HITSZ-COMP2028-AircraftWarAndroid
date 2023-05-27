package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import net.imshit.aircraftwar.element.generate.enemy.HardEnemyGenerateStrategy

class HardGame(context: Context, attrs: AttributeSet?, soundMode: Boolean, handler: Handler) :
    Games(
        context = context, attrs = attrs, soundMode = soundMode, handler = handler
    ) {

    override val generateStrategy = HardEnemyGenerateStrategy(this)

    override fun init() {
        super.init()
        this.background = images.backgroundHard
    }
}