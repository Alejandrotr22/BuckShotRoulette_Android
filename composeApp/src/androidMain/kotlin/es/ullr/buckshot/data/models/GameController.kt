package es.ullr.buckshot.data.models

data class GameController(
    var gun: Gun,
    var state: GameState,
    val gameOver: Boolean,
    var winner: String?,
    var round: Int,
    val winningPoints: Int,
    val players: List<Player>,
    var turn: String? = "",
    var hint: String = "",
    var adrenalineUsed: Boolean = false,
)
