package net.imshit.aircraftwar.data.scoreboard

import java.util.Date

data class ScoreInfo(
    val id: Int, val name: String, val score: Int, val time: Date
)