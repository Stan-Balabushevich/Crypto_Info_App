package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.data.coin
import id.slava.nt.cryptocurrencyinfoapp.data.repository.FakeCoinRepository
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class GetCoinsUseCaseTest{

    private lateinit var coinRepository: FakeCoinRepository
    private lateinit var getCoinsUseCase: GetCoinsUseCase

    @BeforeEach
    fun setUp(){

        coinRepository = FakeCoinRepository()
        getCoinsUseCase = GetCoinsUseCase(coinRepository)

    }

    private val testCoins = listOf(
        coin().copy(id = "btc 11"),
        coin().copy(id = "xpr 45"),
        coin().copy(id = "eth 89")
    )

    @Test
    fun `getCoins emits Loading, then Success`() = runTest {

        // Set up the fake repository
        coinRepository.apply {
            addCoins(testCoins)
            shouldReturnError = false
        }

        // Collect emissions from the use case
        val results = getCoinsUseCase().take(2).toList()

        // Assertions
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Success)
        assertTrue(results[1].data ==  testCoins)


    }


}