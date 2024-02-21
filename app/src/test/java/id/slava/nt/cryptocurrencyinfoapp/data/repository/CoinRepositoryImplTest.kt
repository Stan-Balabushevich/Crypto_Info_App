package id.slava.nt.cryptocurrencyinfoapp.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.google.gson.Gson
import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.data.coinDetailKtorDto
import id.slava.nt.cryptocurrencyinfoapp.data.coinDto
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.CoinKtorApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.toCoinDetail
import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.HttpException
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

        coinKtorApi = FakeCoinKtorApi()


        coinRepository = CoinRepositoryImpl(api = coinApi, ktorClient = coinKtorApi, database = coindatabase )

    }

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
    fun `Response IOException error - MockWebServer`() = runBlocking {
        // Enqueue a response
        mockWebServer.enqueue(MockResponse().setBody("test body"))

        // Declare a variable to hold the result outside the coroutine
        lateinit var result: List<Resource<List<Coin>>>

        // Start a request
        val job = launch {
            result = coinRepository.getCoins().take(2).toList()
        }

        // Wait a bit to ensure the request is made before shutting down the server
        delay(100) // Adjust this delay based on the response time of your server

        // Shut down the server to simulate a connection failure
        mockWebServer.shutdown()

        // Wait for the job to complete
        job.join()

        // Assertions
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        // Update the assertion to match the actual error message for IOException
        assertThat(result[1].message).isEqualTo("Couldn't reach server. Check your internet connection.")
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

    @Test
    fun `getCoinById emits Loading, Success and data correctly`() = runTest {
        val coinId = "btc-bitcoin"

        // Act
        val result = coinRepository.getCoinById(coinId).toList()

        // Assert Loading state
        assertTrue(result[0] is Resource.Loading, "First emission should be Loading")

        // Assert Success state and data
        assertTrue(result[1] is Resource.Success, "Second emission should be Success")
        val successResult = result[1] as Resource.Success<CoinDetail>
        assertThat(successResult.data).isEqualTo(coinDetailKtorDto().copy(id = coinId).toCoinDetail())
    }

    @Test
    fun `getCoinById emits Error on HttpException`() = runTest {

         val fakeKtorClient = mock(CoinKtorApi::class.java)
         val coinRepository = CoinRepositoryImpl(api = coinApi, ktorClient = fakeKtorClient, database = coindatabase )

        val coinId = "btc-bitcoin"
        val errorMessage = "Server response error"
        val httpException = Mockito.mock(HttpException::class.java)

        // Mock the behavior of the ktorClient to throw an HttpException
        Mockito.`when`(fakeKtorClient.getCoinById(coinId)).thenThrow(httpException)

        // Capture the emitted values
        val result = coinRepository.getCoinById(coinId).toList()

        // Assert that the first emission is Loading
        assertTrue(result[0] is Resource.Loading)

        // Assert that the second emission is an Error
        assertTrue(result[1] is Resource.Error)
        val errorResult = result[1] as Resource.Error
        assertEquals(errorMessage, errorResult.message)
    }

    @Test
    fun `getCoinById emits Error on IOException`() = runTest {
        val fakeApi = FakeCoinKtorApi().apply { shouldThrowIOException = true }
        val coinRepository = CoinRepositoryImpl(api = coinApi, ktorClient = fakeApi, database = coindatabase )

        val result = coinRepository.getCoinById("btc-bitcoin").toList()

        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        val errorResult = result[1] as Resource.Error
        assertEquals("Couldn't reach server. Check your internet connection.", errorResult.message)
    }





}