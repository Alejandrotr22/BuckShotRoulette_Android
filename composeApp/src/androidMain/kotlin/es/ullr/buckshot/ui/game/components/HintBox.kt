package es.ullr.buckshot.ui.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import es.ullr.buckshot.ui.game.GameModelState
import es.ullr.buckshot.ui.game.GameViewModel

@Composable
fun HintBox(modifier: Modifier = Modifier, gameViewModel: GameViewModel, state: GameModelState) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier
            .background(Color.Gray)
            .size(200.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { gameViewModel.clearHint() },
    ) {
        Text(
            "${state.game?.gameController?.hint}",
            modifier = Modifier
                .align(Alignment.Center),
            color = Color.White
        )
    }
}