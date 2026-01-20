package es.ullr.buckshot.ui.game.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import es.ullr.buckshot.ui.game.GameModelState
import es.ullr.buckshot.ui.game.GameViewModel

@Composable
fun ShootOverlay(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
    state: GameModelState
) {

    val interactionSource = remember { MutableInteractionSource() }

    val player1Turn = state.game?.gameController?.turn == "1"

    Box(
        modifier
            .fillMaxSize()
            .zIndex(5f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {},
    ) {
        Column(
            modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.shoot(state.game?.gameController?.turn, "1")
                    viewModel.toggleShootOverlay()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
                    .background(Color.DarkGray.copy(alpha = 0.4f))
                    .then(
                        if (!player1Turn) {
                            Modifier.graphicsLayer(
                                rotationX = 180f,
                                scaleX = -1f,
                                transformOrigin = TransformOrigin(0.5f, 0.5f)
                            )
                        } else {
                            Modifier
                        }
                    )

            ) {
                val text = if (player1Turn) "Shoot opponent" else "Shoot yourself"
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = text,
                        fontSize = 30.sp
                    )
                }
            }
            TextButton(
                onClick = {
                    viewModel.shoot(state.game?.gameController?.turn, "2")
                    viewModel.toggleShootOverlay()
                },
                Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .background(Color.DarkGray.copy(alpha = 0.4f))
                    .then(
                        if (!player1Turn) {
                            Modifier.graphicsLayer(
                                rotationX = 180f,
                                scaleX = -1f,
                                transformOrigin = TransformOrigin(0.5f, 0.5f)
                            )
                        } else {
                            Modifier
                        }
                    ),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
            ) {
                val text = if (player1Turn) "Shoot yourself" else "Shoot opponent"
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text, fontSize = 30.sp)
                }

            }
        }
    }
}