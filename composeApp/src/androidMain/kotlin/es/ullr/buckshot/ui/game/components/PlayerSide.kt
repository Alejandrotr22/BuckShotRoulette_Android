package es.ullr.buckshot.ui.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import buckshotroullete.composeapp.generated.resources.Res
import buckshotroullete.composeapp.generated.resources.display
import buckshotroullete.composeapp.generated.resources.life
import es.ullr.buckshot.data.models.Player
import es.ullr.buckshot.ui.game.GameModelState
import es.ullr.buckshot.ui.game.GameViewModel
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.collectAsState

@Composable
fun PlayerSide(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
    state: GameModelState,
    player: Player?
) {
    Column(modifier.fillMaxSize()) {
        PlayerHeader(
            modifier = Modifier.weight(0.1f),
            player = player
        )
        PlayerBody(
            modifier = Modifier.weight(0.9f),
            player = player,
            viewModel = viewModel
        )
    }
}


@Composable
fun PlayerHeader(
    modifier: Modifier = Modifier,
    player: Player?
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        PlayerIndicators(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )


        PlayerStatus(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            status = "Exposado"
        )
    }
}

@Composable
fun PlayerIndicators(modifier: Modifier = Modifier) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(2) {
            Box(
                Modifier
                    .size(20.dp)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Box(
                    Modifier
                        .size(10.dp)
                        .background(Color.White, CircleShape)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun PlayerStatus(modifier: Modifier = Modifier, status: String) {
    Box(modifier, contentAlignment = Alignment.CenterEnd) {
        Text(text = status)
    }
}

@Composable
fun PlayerBody(
    modifier: Modifier = Modifier,
    player: Player?,
    viewModel: GameViewModel
) {
    if (player != null) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Bottom
        ) {
            PlayerColumn(modifier = Modifier.weight(1f), player, 0, viewModel)

            PlayerLives(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth(),
                current = player.currentHealth ?: 0,
                max = player.maxHealth ?: 0
            )
            PlayerColumn(modifier = Modifier.weight(1f), player, 2, viewModel)
        }
    }

}

@Composable
fun PlayerColumn(
    modifier: Modifier = Modifier,
    player: Player,
    fromIndex: Int,
    viewModel: GameViewModel
) {
    var index = fromIndex

    Column(
        modifier.border(2.dp, Color.White)
    ) {
        repeat(3) { i ->
            val item = player.objects[index]
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                if (item != null) {

                    IconButton(modifier = Modifier.fillMaxSize(), onClick = {
                        val otherPlayer = viewModel.state.value.game?.gameController?.players?.find { it -> it.id != player.id }

                        if (viewModel.state.value.game?.gameController?.adrenalineUsed == false){
                            viewModel.useObject(player.id,otherPlayer?.id ?: "2", item)
                        }else {
                            viewModel.useObject(otherPlayer?.id ?: "2", player.id, item)
                        }

                    }) {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = "Object",
                            contentScale = ContentScale.Fit
                        )

                    }

                }
            }
            if (i < 2) HorizontalDivider(thickness = 2.dp, color = Color.White)

            index += 1
        }
    }
}

@Composable
fun PlayerLives(
    modifier: Modifier = Modifier,
    current: Int,
    max: Int
) {


    val density = LocalDensity.current
    var boxHeight by remember { mutableStateOf(0) }
    var textHeight by remember { mutableStateOf(0) }



    BoxWithConstraints(
        modifier
            .paint(
                painter = painterResource(Res.drawable.display),
                contentScale = ContentScale.Fit
            )
            .onGloballyPositioned { coordinates ->
                boxHeight = with(density) {
                    coordinates.size.height
                }
            }
    ) {

        Row(
            Modifier
                .align(Alignment.TopCenter)
                .onGloballyPositioned { coordinates ->
                    textHeight = with(density) {
                        coordinates.size.height / 2
                    }
                }
                .padding(horizontal = maxWidth * 0.15f)
                .offset {
                    val targetCenterY = boxHeight * 0.625f
                    val childTop = targetCenterY - (textHeight)
                    IntOffset(0, childTop.toInt())
                }
        ) {
            println("Current: $current, Max: $max")
            repeat(max) { index ->
                if (index <= current - 1) {
                    Image(
                        painter = painterResource(Res.drawable.life),
                        contentDescription = "Life",
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                } else {
                    Box(
                        Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}
