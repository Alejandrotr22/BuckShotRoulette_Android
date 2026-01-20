package es.ullr.buckshot.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.ullr.buckshot.data.models.GameState
import es.ullr.buckshot.data.models.Object
import es.ullr.buckshot.domain.GameModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow<GameModelState>(GameModelState())
    val state: StateFlow<GameModelState> = _state.asStateFlow()

    init {
        val current = _state.value
        _state.value = current.copy(
            game = GameModel()
        )
        _state.value.game?.preloadGame()
        Log.d("Log", "state:" + _state.value.game?.gameController?.state)
        startGameLoop()
    }

    fun startGameLoop() {
        viewModelScope.launch(Dispatchers.Default) {
            val fps = 30
            val frameTime = 1000L / fps

            while (isActive) {
                val current = _state.value
                val start = System.currentTimeMillis()
                val updatedGame = current.game?.updateGame()

                _state.update { current.copy(game = updatedGame) }

                when (_state.value.game?.gameController?.state) {
                    GameState.NEW_OBJECTS -> {

                        current.game?.changeState(GameState.NEW_OBJECTS_END)

                    }

                    GameState.NEW_OBJECTS_END -> {
                        // Animaciones
                        println("New objects generating...")
                        delay(4000)
                        println("Objects generated.")
                        current.game?.changeState(GameState.LOADING_GUN)

                    }

                    GameState.WAITING -> {

                    }

                    GameState.NEW_ROUND -> {
                        current.game?.changeState(GameState.NEW_ROUND_END)
                    }

                    GameState.NEW_ROUND_END -> {
                        println("New round starting...")
                        delay(4000)
                        println("Round started.")
                        current.game?.changeState(GameState.NEW_OBJECTS)

                    }

                    GameState.PLAYER_TURN_END -> {
                        println("New turn starting...")
                        delay(3000)
                        println("Turn started.")
                        current.game?.changeState(GameState.PLAYER_TURN)
                    }

                    GameState.WINNER -> {}

                    GameState.LOADING_GUN -> {
                        current.game?.changeState(GameState.LOADING_GUN_END)
                    }

                    GameState.LOADING_GUN_END -> {
                        println("New round starting...")
                        delay(5000)
                        println("Round started.")
                        current.game?.changeState(GameState.NEW_TURN_END)

                    }
                    GameState.NEW_TURN_END -> {
                        println("New round starting...")
                        delay(3000)
                        println("Round started.")
                        current.game?.changeState(GameState.PLAYER_TURN)
                    }
                    else -> {}
                }

                val elapsed = System.currentTimeMillis() - start
                val delayTime = frameTime - elapsed
                if (delayTime > 0) delay(delayTime)
            }
        }
    }


    fun changeState() {
        val current = _state.value
        current.game?.changeState(GameState.NEW_ROUND)
    }

    fun shoot(shooterName: String?, targetName: String?) {
        val current = _state.value
        current.game?.shoot(shooterName, targetName)
    }

    fun useObject(fromPlayerId: String, toPlayerId: String, objectName: Object) {
        val current = _state.value
        current.game?.applyObject(fromPlayerId, toPlayerId, objectName,0)
    }

    fun clearHint() {
        val current = _state.value
        current.game?.gameController?.hint = ""
    }

    fun toggleShootOverlay() {
        val current = _state.value
        viewModelScope.launch(Dispatchers.Default) {
            delay(1000)
        }

        current.shootOverlay = !current.shootOverlay
    }




}