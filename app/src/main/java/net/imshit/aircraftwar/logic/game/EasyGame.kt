package net.imshit.aircraftwar.logic.game

import android.content.Context
import android.util.AttributeSet
import net.imshit.aircraftwar.element.generate.enemy.EasyEnemyGenerateStrategy

class EasyGame(context: Context, attrs: AttributeSet?, soundMode: Boolean) : Games(
    context = context, attrs = attrs, soundMode = soundMode
) {
    constructor(context: Context, soundMode: Boolean) : this(
        context = context, attrs = null, soundMode = soundMode
    )

    /** used by design tool */
    constructor(context: Context, attrs: AttributeSet?) : this(
        context = context, attrs = attrs, soundMode = false
    )

    override val generateStrategy = EasyEnemyGenerateStrategy(this)

    override fun init() {
        super.init()
        this.background = images.backgroundEasy
    }
}