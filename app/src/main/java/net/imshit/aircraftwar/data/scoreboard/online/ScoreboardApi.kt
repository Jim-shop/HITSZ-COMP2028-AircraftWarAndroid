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

package net.imshit.aircraftwar.data.scoreboard.online

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date
import java.util.concurrent.TimeUnit

interface ScoreboardApi {
    @GET(".")
    suspend fun get(
        @Query("token") token: String,
        @Query("mode") mode: String
    ): List<ScoreInfoOnline>

    @FormUrlEncoded
    @PUT(".")
    suspend fun send(
        @Field("token") token: String,
        @Field("score") score: Int,
        @Field("mode") mode: String,
        @Field("time") time: String
    )

    @DELETE("{id}")
    suspend fun delete(
        @Path("id") id: Int,
        @Query("token") token: String
    )

    companion object Utils {
        private const val BASE_URL = "https://haxiaoshen.top/scoreboard/"

        val api: ScoreboardApi by lazy {
            val client = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build()
            val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter())
                .addLast(KotlinJsonAdapterFactory()).build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            retrofit.create(ScoreboardApi::class.java)
        }
    }
}