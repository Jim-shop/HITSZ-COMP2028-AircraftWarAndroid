package net.imshit.aircraftwar.logic

import android.content.Context
import android.util.AttributeSet

class HardGame(context: Context, attrs: AttributeSet?, soundMode: Boolean) : Games(
    context = context, attrs = attrs, soundMode = soundMode
) {
    constructor(context: Context, soundMode: Boolean) : this(
        context = context, attrs = null, soundMode = soundMode
    )

    /** used by design tool */
    constructor(context: Context, attrs: AttributeSet?) : this(
        context = context, attrs = attrs, soundMode = false
    )

    override val background = images.backgroundHard
    // TODO
}