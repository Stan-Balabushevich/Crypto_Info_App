package id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.presentation.theme.ActiveGreen
import id.slava.nt.cryptocurrencyinfoapp.presentation.theme.NotActiveRed

@Composable
fun CoinItemCard(coin: Coin,
                     onCoinItemSelected: (Coin) -> Unit) {
    Card (
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCoinItemSelected(coin) }
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if(coin.isActive) "active" else "inactive",
                color = if(coin.isActive) ActiveGreen else NotActiveRed,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(CenterVertically),

            )
        }
    }
}