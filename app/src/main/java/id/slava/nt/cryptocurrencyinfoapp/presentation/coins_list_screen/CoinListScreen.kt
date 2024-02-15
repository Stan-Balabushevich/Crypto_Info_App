package id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import id.slava.nt.cryptocurrencyinfoapp.common.Constants
import id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen.components.CoinItemCard
import id.slava.nt.cryptocurrencyinfoapp.presentation.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CoinListScreen(
    viewModel: CoinsListViewModel = hiltViewModel(),
    navController: NavController
){

    val state = viewModel.state.value
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            // Your floating action button code
        }
    ) {
        // This Box will fill the entire available space
        Box(modifier = Modifier.fillMaxSize()) {
            // LazyColumn for your list
            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                items(items = state.coins) { coin ->
                    CoinItemCard(coin = coin, onCoinItemSelected = { coinNav ->
                        // arguments saved in savedHandleState
                        navController.navigate(
                            Screen.CoinDetailScreen.route
                            + "?${Constants.PARAM_COIN_ID}=${coinNav.id}")

                    })
                }
            }

            // Error message
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }

            // Loading indicator
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}