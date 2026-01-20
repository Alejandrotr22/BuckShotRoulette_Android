package es.ullr.buckshot.ui.game

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import es.ullr.buckshot.data.models.Player
import org.koin.compose.viewmodel.koinViewModel
import buckshotroullete.composeapp.generated.resources.Res

import es.ullr.buckshot.data.models.GameState
import es.ullr.buckshot.ui.game.components.GunSide
import es.ullr.buckshot.ui.game.components.HintBox
import es.ullr.buckshot.ui.game.components.PlayerSide
import es.ullr.buckshot.ui.game.components.ShootOverlay
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import kotlin.text.repeat

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val gameViewModel = koinViewModel<GameViewModel>()
    val state by gameViewModel.state.collectAsState()

    Box(modifier.fillMaxSize()) {
        GameWindow(modifier.align(Alignment.Center), gameViewModel, state)
        if (state.shootOverlay) {
            ShootOverlay(modifier.align(Alignment.Center), gameViewModel, state)
        }
        if (state.game?.gameController?.hint != "") {
            HintBox(modifier.align(Alignment.Center), gameViewModel, state)
        }

    }
}

@Composable
fun GameWindow(modifier: Modifier = Modifier, viewModel: GameViewModel, state: GameModelState) {

    val players = state.game?.gameController?.players
    println("chamber: ${state.game?.gameController?.gun?.chamber}")
    Column(
        Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .rotate(180f)
        ) {
            PlayerSide(
                Modifier
                    .fillMaxSize(),
                viewModel = viewModel,
                state = state,
                players?.find { player -> player.id == "2" }?.copy()
            )
        }

        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()

        ) {
            GunSide(modifier, viewModel, state)
        }
        PlayerSide(
            Modifier
                .weight(3f)
                .fillMaxWidth(),
            viewModel = viewModel,
            state = state,
            players?.find { player -> player.id == "1" }?.copy()
        )
    }

}

@Preview
@Composable
private fun Example () {
    val density = LocalDensity.current

    var parentHeightPx by remember { mutableStateOf(0) }
    var childHeightPx by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                parentHeightPx = coordinates.size.height
            }
    ) {
        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    childHeightPx = coordinates.size.height
                }
                .offset {
                    // Queremos colocar el centro del hijo en el 35% de la altura del padre
                    val targetCenterY = parentHeightPx * 0.50f
                    val childTop = targetCenterY - (childHeightPx / 2f)
                    IntOffset(0, childTop.toInt())
                }
                .align(Alignment.TopCenter)
                .background(Color.Cyan)
        ) {
            Text(
                "Hijo centrado al 35%",
                modifier = Modifier.size(200.dp).padding(8.dp),
                color = Color.Black
            )
        }
    }
}


