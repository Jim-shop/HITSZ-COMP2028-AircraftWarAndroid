package net.imshit.aircraftwar.data.account

sealed interface AccountDao {
    fun load(): AccountInfo?
    fun store(accountInfo: AccountInfo)
    fun clear()
}