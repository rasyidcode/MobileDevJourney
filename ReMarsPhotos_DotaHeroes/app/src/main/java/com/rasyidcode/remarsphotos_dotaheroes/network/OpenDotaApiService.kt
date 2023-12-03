package com.rasyidcode.remarsphotos_dotaheroes.network

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.opendota.com/api"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor {
        val request = it.request()

        // Modify request body
        val buffer = Buffer()
        request.body?.writeTo(buffer)
        val requestBodyJson = buffer.readUtf8()

        val jsonMapType = Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
        val jsonAdapter: JsonAdapter<Map<String, Any?>> = moshi.adapter(jsonMapType)
        val responseBodyMap = jsonAdapter.fromJson(requestBodyJson)

        Log.d(OpenDotaApiService::class.simpleName, "responseBodyMap: $responseBodyMap")

//        val newBody = "".toRequestBody(request.body?.contentType())
        // return@addInterceptor response (Explicit)
        it.proceed(request)// (Implicit)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OpenDotaApiService {
    @GET("constants/heroes")
    suspend fun getHeroes(): List<DotaHero>
}

object OpenDotaApi {
    val apiService: OpenDotaApiService by lazy {
        retrofit.create(OpenDotaApiService::class.java)
    }
}