package com.rasyidcode.remarsphotos_dotaheroes

import com.rasyidcode.remarsphotos_dotaheroes.network.OpenDotaApiService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader

class OpenDotaAPIServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var openDotaAPIService: OpenDotaApiService
    private lateinit var okHttpClient: OkHttpClient

    @Before
    fun setup() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        okHttpClient = OkHttpClient.Builder()
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

        mockWebServer = MockWebServer()
        openDotaAPIService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(OpenDotaApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_customInterceptor() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("heroes.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val heroesJson = reader.use { it.readText() }

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(heroesJson)
        )

        val baseUrl = mockWebServer.url("/")
        val request = Request.Builder().url(baseUrl).build()
        val response = okHttpClient.newCall(request).execute()

        println("response: ${response.body?.string()}")

        assert(response.isSuccessful)
    }

    @Test
    fun test_getHeroes_success() = runBlocking {
        val inputStream = javaClass.classLoader?.getResourceAsStream("heroes.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val heroesJson = reader.use { it.readText() }

        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(heroesJson))

        val data = openDotaAPIService.getHeroes()

        assert(data[0].localizedName == "Anti-Mage")
    }
}