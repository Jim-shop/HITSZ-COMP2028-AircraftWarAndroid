package net.imshit.aircraftwar.data.scoreboard

import java.io.Closeable

sealed interface ScoreboardDao : Closeable {
    /**
     * 返回最好的k条记录
     *
     * @param topK 要返回的记录数。=-1时，返回所有记录
     * @return 返回记录列表
     */
    fun getTopK(topK: Int = -1): List<ScoreInfo>

    /**
     * 向计分板添加项目（去重）
     *
     * @param item 项目
     */
    fun addItem(item: ScoreInfo)

    /**
     * 从计分板删除项目
     *
     * @param item 项目
     */
    fun deleteItem(item: ScoreInfo)

    /**
     * 从计分板删除项目
     *
     * @param indices 内部序号
     */
    fun deleteItem(indices: IntArray)

    fun clear()

    /**
     * 关闭计分板数据模型（通常来说意味着缓存写回）
     */
    override fun close()
}