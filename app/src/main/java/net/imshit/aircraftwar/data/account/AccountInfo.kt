package net.imshit.aircraftwar.data.account

import java.time.LocalDateTime

data class AccountInfo (
    val account: String,
    val token: String,
    val loginTime: LocalDateTime,
)