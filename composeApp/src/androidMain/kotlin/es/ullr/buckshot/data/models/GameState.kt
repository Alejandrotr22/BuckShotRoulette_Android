package es.ullr.buckshot.data.models

enum class GameState {
    WAITING,
    NEW_ROUND,
    NEW_ROUND_END,
    NEW_OBJECTS,
    NEW_OBJECTS_END,
    PLAYER_TURN,
    PLAYER_TURN_END,
    LOADING_GUN,
    LOADING_GUN_END,
    NEW_TURN_END,
    WINNER
}