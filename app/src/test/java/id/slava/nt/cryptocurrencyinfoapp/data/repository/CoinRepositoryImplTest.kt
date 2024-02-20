package id.slava.nt.cryptocurrencyinfoapp.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.google.gson.Gson
import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.data.coin
import id.slava.nt.cryptocurrencyinfoapp.data.coinDto
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.CoinKtorApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class CoinRepositoryImplTest{

    private lateinit var coindatabase: CoinDatabase
    private lateinit var mockWebServer: MockWebServer
    private lateinit var coinApi: CoinPaprikaApi
    private lateinit var coinKtorApi: CoinKtorApi
    private lateinit var coinRepository: CoinRepositoryImpl

    @BeforeEach
    fun setUp(){

        coindatabase = FakeDatabase()
        mockWebServer = MockWebServer()

        coinApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()

        coinKtorApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()


        coinRepository = CoinRepositoryImpl(api = coinApi, ktorClient = coinKtorApi, database = coindatabase )

    }

    private val testCoins = listOf(
        coin().copy(id = "btc 11"),
        coin().copy(id = "xpr 45"),
        coin().copy(id = "eth 89")
    )

    private val testCoinsEntities = listOf(
        CoinEntity().apply { id = "123" },
        CoinEntity().apply { id = "456" },
        CoinEntity().apply { id = "789" }
    )

    private val testCoinsDto = listOf(
        coinDto().copy(id = "123" ),
        coinDto().copy(id = "456" ),
        coinDto().copy(id = "789" )
    )

    private val testJson = Gson().toJson(testCoinsDto)


    @Test
    fun `Response HttpException error - MockWebServer`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(404)
        )
        val result = coinRepository.getCoins().take(2).toList()

        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(result[1].message).isEqualTo("HttpException error occurred")
    }

    @Test
    fun `getCoins emits Loading first`() = runTest {

        val result = coinRepository.getCoins().toList()

        assertTrue(result.first() is Resource.Loading)
    }

    @Test
    fun `getCoins emits Success with local data when available`() = runTest {

        coindatabase.saveCoins(testCoinsEntities)

        val result = coinRepository.getCoins().toList()

        assertTrue(result[1] is Resource.Success)
        assertThat(result[1].data).isEqualTo(testCoinsEntities.map { it.toCoin() })
    }

    @Test
    fun `getCoins saved to database`() = runTest {

        coindatabase.saveCoins(testCoinsEntities)

        val result = coindatabase.getCoins().first()

        assertThat(result).isEqualTo(testCoinsEntities)
    }

    @Test
    fun `getCoins fetches remote data when local data is empty`() = runTest {
        coindatabase.saveCoins(emptyList())

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(testJson)
        )
        val result = coinRepository.getCoins().take(2).toList()

        assertTrue(result[1] is Resource.Success)
        assertThat(result[1].data).isEqualTo(testCoinsDto.map { it.toCoin() })
    }
    
}