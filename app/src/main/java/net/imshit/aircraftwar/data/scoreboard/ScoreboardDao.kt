/*
 * Copyright (c) [2023] [Jim-shop]
 * [AircraftwarAndroid] is licensed under Mulan PubL v2.
 * You can use this software according to the terms and conditions of the Mulan PubL v2.
 * You may obtain a copy of Mulan PubL v2 at:
 *          http://license.coscl.org.cn/MulanPubL-2.0
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PubL v2 for more details.
 */

package net.imshit.aircraftwar.data.scoreboard

interface ScoreboardDao {
    /**
     * 返回最好的k条记录
     *
     * @param topK 要返回的记录数。=-1时，返回所有记录
     * @return 返回记录列表
     */
    fun getTopK(topK: Int = -1): List<ScoreInfo>
    fun getAll() = getTopK(-1)

    /**
     * 向计分板添加项目（去重）
     *
     * @param item 项目
     */
    fun addItem(item: ScoreInfo)

    /**
     * 从计分板删除项目
     *
     * @param id 内部序号
     */
    fun deleteItem(id: Int): Boolean
}