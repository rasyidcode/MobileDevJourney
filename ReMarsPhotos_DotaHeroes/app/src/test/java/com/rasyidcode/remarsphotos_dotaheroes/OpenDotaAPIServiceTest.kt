package com.rasyidcode.remarsphotos_dotaheroes

import com.rasyidcode.remarsphotos_dotaheroes.network.OpenDotaAPI
import com.rasyidcode.remarsphotos_dotaheroes.network.OpenDotaAPIService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStreamReader

class OpenDotaAPIServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var openDotaAPIService: OpenDotaAPIService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        openDotaAPIService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .build()
            .create(OpenDotaAPIService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_getHeroes_success() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("heroes.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val heroesJson = reader.use { it.readText() }

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(heroesJson)
        mockWebServer.enqueue(mockResponse)

        runBlocking {
            val data = openDotaAPIService.getHeroes()

            assert(data.size == 10)
            assert(data[0].name == "Anti Mage")
        }
    }

}