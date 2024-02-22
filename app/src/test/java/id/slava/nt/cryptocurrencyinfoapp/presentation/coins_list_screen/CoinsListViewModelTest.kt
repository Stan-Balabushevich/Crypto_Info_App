package id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen


import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import id.slava.nt.cryptocurrencyinfoapp.data.repository.FakeCoinRepository
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.CoinUseCases
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.GetCoinByIdUseCase
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.GetCoinsDbUseCase
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.GetCoinsUseCase
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.SaveCoinsLocalUseCase
import id.slava.nt.cryptocurrencyinfoapp.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class CoinsListViewModelTest {

    private lateinit var viewModel: CoinsListViewModel
    private lateinit var useCases: CoinUseCases
    private lateinit var getCoins: GetCoinsUseCase
    private lateinit var getCoinById: GetCoinByIdUseCase
    private lateinit var saveCoinsLocal: SaveCoinsLocalUseCase
    private lateinit var getCoinsDb: GetCoinsDbUseCase
    private lateinit var coinRepository: FakeCoinRepository

    @BeforeEach
    fun setUp(){

        coinRepository = FakeCoinRepository()
        getCoins = GetCoinsUseCase(coinRepository)
        getCoinById = GetCoinByIdUseCase(coinRepository)
        getCoinsDb = GetCoinsDbUseCase(coinRepository)
        saveCoinsLocal = SaveCoinsLocalUseCase(coinRepository)
        useCases = CoinUseCases(getCoins = getCoins, getCoinById = getCoinById, saveCoinsLocal = saveCoinsLocal, getCoinsDb = getCoinsDb)

    }

    private fun initViewModel(){
//        if (!::viewModel.isInitialized){
        viewModel = CoinsListViewModel(useCases = useCases)
//        }
    }

    @Test
    fun `Test loading state updates`() = runTest {

        initViewModel()

        viewModel.stateFlow.test {

            // first emission with initial state false
            val emission1 = awaitItem()
            assertThat(emission1.isLoading).isFalse()
            Assertions.assertEquals(viewModel.stateFlow.value.coins, emission1.coins)


            val emission2 = awaitItem()
            assertThat(emission2.isLoading).isTrue()

            val emission3 = awaitItem()
            assertThat(emission3.isLoading).isFalse()
            Assertions.assertEquals(viewModel.stateFlow.value.coins, emission3.coins)

            // The cancelAndIgnoreRemainingEvents() function will then ensure the coroutine is then cancelled and exits the test block.
            // Without it, there may be some unconsumed events coming from this call that we don’t actually care about in the test.
            // In our case we consume all 3 events and we do not really need it.
            cancelAndIgnoreRemainingEvents()

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test loading coin detail success`() = runTest {

        initViewModel()
        coinRepository.saveCoinsLocally()

        advanceUntilIdle()

        assertThat(viewModel.stateFlow.value.coins).isEqualTo(coinRepository.coins)
        assertThat(viewModel.stateFlow.value.isLoading).isFalse()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Test loading profile error`() = runTest {
        coinRepository.shouldReturnError = true

        initViewModel()

        advanceUntilIdle()

        assertThat(viewModel.stateFlow.value.coins).isEqualTo(emptyList())
        assertThat(viewModel.stateFlow.value.error).isEqualTo("Test error")
        assertThat(viewModel.stateFlow.value.isLoading).isFalse()
    }

}




