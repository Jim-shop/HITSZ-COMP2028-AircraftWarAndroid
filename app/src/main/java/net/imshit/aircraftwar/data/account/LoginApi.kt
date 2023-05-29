package net.imshit.aircraftwar.data.account

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface LoginApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("user") account: String, @Field("password") encoded_password: String
    ): String

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("user") account: String, @Field("password") encoded_password: String
    ): String

    companion object Utils {
        private const val BASE_URL = "https://haxiaoshen.top/"

        val api: LoginApi by lazy {
            val client = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(ScalarsConverterFactory.create()).build()
            retrofit.create(LoginApi::class.java)
        }

    }
}