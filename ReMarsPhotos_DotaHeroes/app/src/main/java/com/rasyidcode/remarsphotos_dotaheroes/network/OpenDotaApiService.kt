package com.rasyidcode.remarsphotos_dotaheroes.network

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.opendota.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor {
        val request = it.request()
        val response: Response = it.proceed(request)

        val jsonMapType =
            Types.newParameterizedType(Map::class.java, String::class.java, Any::class.java)
        val jsonAdapter: JsonAdapter<Map<String, Any?>> = moshi.adapter<Map<String, Any?>?>(jsonMapType).lenient()
        val responseBodyMap = response.body?.string()
            ?.let { it1 -> jsonAdapter.fromJson(it1) }
            ?: emptyMap()

        val heroList = mutableListOf<Map<String, Any?>>()
        responseBodyMap.entries.forEach { heroMap ->
            heroList.add(heroMap.value as Map<String, Any?>)
        }

        val listMapType =
            Types.newParameterizedType(List::class.java, Map::class.java, String::class.java, Any::class.java)
        val listMapAdapter: JsonAdapter<List<Map<String, Any?>>> = moshi.adapter(listMapType)
        val heroListJsonString = listMapAdapter.toJson(heroList)

        response.newBuilder()
            .body(heroListJsonString.toResponseBody(response.body?.contentType()))
            .build()
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
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