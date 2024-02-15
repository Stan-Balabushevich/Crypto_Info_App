package id.slava.nt.cryptocurrencyinfoapp.presentation.coin_detail_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.slava.nt.cryptocurrencyinfoapp.common.Constants
import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.use_case.CoinUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: CoinUseCases):
    ViewModel() {


    private val _state = mutableStateOf(CoinState())
    val state: State<CoinState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { id ->
            getCoin(id)
        }
    }

    private fun getCoin(coinId: String) {

        viewModelScope.launch {
            useCases.getCoinById(coinId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CoinState(coin = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = CoinState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = CoinState(isLoading = true)
                    }
                }
            }
        }
    }

}