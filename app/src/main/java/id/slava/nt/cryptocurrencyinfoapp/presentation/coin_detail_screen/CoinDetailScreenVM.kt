package id.slava.nt.cryptocurrencyinfoapp.presentation.coin_detail_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CoinDetailScreenVM(
    viewModel: CoinDetailViewModel = hiltViewModel()
){
    CoinDetailScreen(coinState = viewModel.stateFlow.collectAsState().value)
}