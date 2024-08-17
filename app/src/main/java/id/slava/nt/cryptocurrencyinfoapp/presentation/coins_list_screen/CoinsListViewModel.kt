package id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.CoinUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val useCases: CoinUseCases
): ViewModel() {


    //if your state is only being observed and modified within the Composables, using MutableState<T>  is perfectly fine.
    // If you foresee a need for more complex state management or sharing state across different parts of your app, consider using StateFlow<T>.
    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    private val _stateFlow = MutableStateFlow(CoinListState())
    val stateFlow: StateFlow<CoinListState> = _stateFlow

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {
            useCases.getCoins().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _stateFlow.value = CoinListState(coins = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _stateFlow.value = CoinListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _stateFlow.value = CoinListState(isLoading = true)
                    }
                }
            }
        }
    }

}


