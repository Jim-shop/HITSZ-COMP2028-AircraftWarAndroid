package net.imshit.aircraftwar.data.scoreboard.online

import java.util.Date

data class ScoreInfoOnline(
    val id: Int,
    val rank: Int,
    val user: String,
    val score: Int,
    val mode: String,
    val time: Date
)