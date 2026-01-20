package es.ullr.buckshot.ui.game.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import buckshotroullete.composeapp.generated.resources.Res
import buckshotroullete.composeapp.generated.resources.centerdisplay
import buckshotroullete.composeapp.generated.resources.empryshellslot
import buckshotroullete.composeapp.generated.resources.shellblank
import buckshotroullete.composeapp.generated.resources.shelllive
import buckshotroullete.composeapp.generated.resources.shutgun
import buckshotroullete.composeapp.generated.resources.tiles
import es.ullr.buckshot.data.models.GameState
import es.ullr.buckshot.ui.game.GameModelState
import es.ullr.buckshot.ui.game.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun GunSide(modifier: Modifier = Modifier, gameViewModel: GameViewModel, state: GameModelState) {

    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        var cardFace by remember { mutableFloatStateOf(0f) }
        val scope = rememberCoroutineScope()

        when (state.game?.gameController?.state) {
            GameState.WAITING -> {
                FlipCard(
                    cardFace,
                    axis = RotationAxis.AxisX,
                    back = {
                        Background(modifier)
                    },
                    front = {
                        Box(
                            modifier
                                .fillMaxSize()
                                .graphicsLayer(clip = true)
                                .background(Color.Red)
                        ) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        cardFace = 180f
                                        delay(2000)
                                        gameViewModel.changeState()
                                        cardFace = 360f
                                    }

                                }, modifier
                                    .align(Alignment.Center)
                            ) {
                                Text("Start Game")
                            }
                        }
                    }
                )

            }

            GameState.LOADING_GUN_END -> {

                LaunchedEffect("") {
                    cardFace = 180f
                    delay(2000)
                    cardFace = 360f
                }
                FlipCard(
                    cardFace,
                    axis = RotationAxis.AxisX,
                    back = {
                        Box(modifier.fillMaxSize()) {
                            Background(modifier.align(Alignment.Center))
                            AmmoCount(modifier.align(Alignment.Center), state)
                        }
                    },
                    front = {
                        Background(modifier)
                    }
                )
            }

            GameState.NEW_ROUND_END -> {

                var visible by remember { mutableStateOf(true) }

                LaunchedEffect("") {
                    cardFace = 180f
                    delay(3000)
                    cardFace = 360f
                }
                LaunchedEffect("") {
                    delay(200)
                    repeat(5 * 2) {
                        visible = !visible
                        delay(300)
                    }
                    visible = true
                }

                FlipCard(
                    cardFace,
                    axis = RotationAxis.AxisX,
                    back = {
                        Box(
                            modifier
                                .fillMaxSize()
                                .graphicsLayer(clip = true)
                                .paint(
                                    painterResource(Res.drawable.tiles),
                                    contentScale = ContentScale.FillWidth
                                )
                        ) {
                            Column(
                                modifier
                                    .fillMaxSize()
                                    .paint(
                                        painterResource(Res.drawable.centerdisplay),
                                        contentScale = ContentScale.FillWidth
                                    ),
                                verticalArrangement = spacedBy(10.dp, Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "¡New Round!",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .graphicsLayer(
                                            rotationX = 180f,
                                            scaleX = -1f,
                                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                                        )
                                        .alpha(if (visible) 1f else 0f),
                                    fontSize = 20.sp,
                                    color = Color.Cyan
                                )
                                Text(
                                    "¡New Round!",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .alpha(if (visible) 1f else 0f),
                                    fontSize = 20.sp,
                                    color = Color.Cyan
                                )
                            }
                        }
                    },
                    front = {
                        Background(modifier.align(Alignment.Center))
                    }
                )
            }

            GameState.NEW_OBJECTS_END -> {

                var visible by remember { mutableStateOf(true) }

                LaunchedEffect("") {
                    cardFace = 180f
                    delay(3000)
                    cardFace = 360f
                }
                LaunchedEffect("") {
                    delay(200)
                    repeat(5 * 2) {
                        visible = !visible
                        delay(300)
                    }
                    visible = true
                }

                FlipCard(
                    cardFace,
                    axis = RotationAxis.AxisX,
                    back = {
                        Box(
                            modifier
                                .fillMaxSize()
                                .graphicsLayer(clip = true)
                                .paint(
                                    painterResource(Res.drawable.tiles),
                                    contentScale = ContentScale.FillWidth
                                )
                        ) {
                            Column(
                                modifier
                                    .fillMaxSize()
                                    .paint(
                                        painterResource(Res.drawable.centerdisplay),
                                        contentScale = ContentScale.FillWidth
                                    ),
                                verticalArrangement = spacedBy(10.dp, Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Text(
                                    "¡New Objects!",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .graphicsLayer(
                                            rotationX = 180f,
                                            scaleX = -1f,
                                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                                        )
                                        .alpha(if (visible) 1f else 0f),
                                    fontSize = 20.sp,
                                    color = Color.Cyan
                                )
                                Text(
                                    "¡New Objects!",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .alpha(if (visible) 1f else 0f),
                                    fontSize = 20.sp,
                                    color = Color.Cyan
                                )
                            }
                        }
                    },
                    front = {
                        Background(modifier)
                    }
                )

            }

            GameState.NEW_TURN_END -> {
                LaunchedEffect("") {
                    cardFace = 180f
                }
                FlipCard(
                    cardFace,
                    axis = RotationAxis.AxisX,
                    back = {
                        Box(modifier.fillMaxSize()) {
                            Background(modifier.align(Alignment.Center))
                            Gun(modifier.align(Alignment.Center), gameViewModel = gameViewModel)
                        }
                    },
                    front = {
                        Background(modifier)
                    }
                )
            }

            GameState.PLAYER_TURN -> {

                var rotation by remember { mutableFloatStateOf(0f) }

                LaunchedEffect("") {
                    rotation = if (state.game.gameController?.turn == "1") {
                        -90f
                    } else {
                        90f
                    }

                }

                Box(modifier.fillMaxSize()) {
                    Background(modifier.align(Alignment.Center))
                    Gun(modifier.align(Alignment.Center), rotation, gameViewModel)
                }
            }


            GameState.WINNER -> {}
            else -> {
                Background(modifier.align(Alignment.Center))

            }
        }


    }

}

@Composable
fun AmmoCount(modifier: Modifier = Modifier, state: GameModelState) {


    Box(
        modifier
            .fillMaxSize()
            .padding(20.dp)
            .border(1.dp, Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = spacedBy(5.dp, Alignment.CenterHorizontally)
        ) {

            repeat(6) { it ->
                Box(
                    Modifier
                        .fillMaxHeight()
                        .border(1.dp, Color(0xf1f1f1f1))
                ) {
                    if (it <= 1) {
                        Image(
                            painterResource(Res.drawable.shelllive),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.Black)
                                .padding(1.dp, 2.dp),
                            colorFilter = ColorFilter.colorMatrix(
                                ColorMatrix().apply {
                                    setToSaturation(2f)
                                }
                            )

                        )
                    } else if (it >= 2 && it < 4) {
                        Image(
                            painterResource(Res.drawable.shellblank),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.Black)
                                .padding(1.dp, 2.dp),
                            colorFilter = ColorFilter.colorMatrix(
                                ColorMatrix().apply {
                                    setToSaturation(2f)
                                }
                            )
                        )
                    } else {
                        Image(
                            painterResource(Res.drawable.empryshellslot),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(1.dp, 2.dp),
                            colorFilter = ColorFilter.colorMatrix(
                                ColorMatrix().apply {
                                    setToSaturation(2f)
                                }
                            )
                        )
                    }


                }
            }


//            repeat(state.game?.gameController?.gun?.realBullets ?: 0) {
//                Icon(
//                    painterResource(Res.drawable.shelllive),
//                    tint = Color.Unspecified,
//                    contentDescription = "",
//                    modifier = Modifier.width(bulletHeight)
//                )
//            }
//            repeat(
//                (state.game?.gameController?.gun?.total
//                    ?: 0) - (state.game?.gameController?.gun?.realBullets ?: 0)
//            ) {
//                Icon(
//                    painterResource(Res.drawable.shellblank),
//                    tint = Color.Unspecified,
//                    contentDescription = "",
//                    modifier = Modifier.width(bulletHeight)
//                )
//            }
//            repeat( 6 - (state.game?.gameController?.gun?.total ?: 6)) {
//                Box(modifier
//                    .fillMaxHeight()
//                    .width(bulletHeight)
//                    .background(Color.Black))
//            }
        }
    }

}

@Composable
fun Background(modifier: Modifier = Modifier) {
    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer(clip = true)
    ) {
        Box(
            modifier
                .fillMaxSize()
                .paint(
                    painterResource(Res.drawable.tiles),
                    contentScale = ContentScale.FillWidth
                )
                .padding(20.dp)
                .border(1.dp, Color.White)

        ) {
        }
    }

}

@Composable
fun Gun(modifier: Modifier = Modifier, gunRotation: Float = 0f, gameViewModel: GameViewModel) {

    val interactionSource = remember { MutableInteractionSource() }

    val rotation = animateFloatAsState(
        targetValue = gunRotation,
        animationSpec = tween(
            durationMillis = 500,
            easing = EaseInOut,
        )
    )

    Box(
        modifier
            .fillMaxSize()

    ) {
        Icon(
            painterResource(Res.drawable.shutgun),
            tint = Color.Unspecified,
            contentDescription = "",
            modifier = Modifier
                .zIndex(10f)
                .align(Alignment.Center)
                .fillMaxWidth()
                .graphicsLayer(
                    rotationZ = rotation.value,
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { gameViewModel.toggleShootOverlay() },

            )
    }

}

@Preview
@Composable
fun GunSidePreview() {
    AmmoCount(Modifier, GameModelState())
}