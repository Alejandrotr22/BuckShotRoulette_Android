package es.ullr.buckshot.ui.game

import es.ullr.buckshot.domain.GameModel

data class GameModelState(
    val game: GameModel? = null,
    var shootOverlay: Boolean = false,
)