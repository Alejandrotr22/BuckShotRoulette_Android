package es.ullr.buckshot.domain

import es.ullr.buckshot.data.models.GameController
import es.ullr.buckshot.data.models.GameState
import es.ullr.buckshot.data.models.Gun
import es.ullr.buckshot.data.models.Object
import es.ullr.buckshot.data.models.ObjectNames
import es.ullr.buckshot.data.models.Objects
import es.ullr.buckshot.data.models.Player
import kotlin.random.Random

data class GameModel(var gameController: GameController? = null, var player1: Player = Player(), var player2: Player = Player()) {

    fun preloadGame(): GameModel {

        player1 = Player("1", "Player 1", 0, 0)
        player2 = Player("2", "Player 2", 0, 0)

        gameController = GameController(
            Gun(),
            GameState.WAITING,
            false,
            null,
            1,
            2,
            listOf(player1, player2),
            turn = player1.id
        )
        return copy(gameController = gameController)
    }

    fun updateGame(): GameModel {

        when (gameController?.state) {
            GameState.WAITING -> {

            }
            GameState.NEW_ROUND -> {
                newRound()
            }
            GameState.NEW_OBJECTS -> {
                newObjects()
            }
            GameState.PLAYER_TURN -> {

                playerTurn()
            }
            GameState.WINNER -> {
                println("winner")
            }
            GameState.LOADING_GUN -> {
                loadGun()
            }
            else -> {}

        }
        return copy(gameController = gameController, player1 = player1.copy(), player2 = player2.copy())

    }

    private fun playerTurn() {
        val currentPlayer = gameController?.players?.find { it.id == gameController?.turn }
        if (currentPlayer?.chainApplied == true) {
            gameController?.turn = if (currentPlayer.id == player1.id) player2.id else player1.id
            currentPlayer.chainApplied = false
        }
    }


    fun newRound() {

        val maxLives = Random.nextInt(2, 6)

        gameController?.players?.forEach {
            it.objects = arrayOfNulls(6)
        }
        gameController?.players?.forEach {
            it.maxHealth = maxLives
            it.currentHealth = maxLives
        }
        copy(gameController?.copy())
    }

    fun newObjects() {
        val objectNum = Random.nextInt(1, 5)
        gameController?.players?.forEach { it ->
            val objects = it.objects

            for (i in 1..objectNum) {
                val firstFreeIndex = objects.indexOfFirst { item -> item == null }
                if (firstFreeIndex != -1) {
                    val obj = Objects.all.random()
                    objects[firstFreeIndex] = obj
                }
            }

            it.objects = objects
        }
    }

    fun loadGun() {
        val totalBullets = Random.nextInt(2, 7)
        val realBullets = Random.nextInt(1, totalBullets)
        val chamber = mutableListOf<Int>()

        for (i in 1..realBullets) {
            chamber.add(1)
        }
        for (i in 1..(totalBullets - realBullets)) {
            chamber.add(0)
        }
        chamber.shuffle()

        gameController?.gun = Gun(
            totalBullets,
            realBullets,
            chamber,
            0
        )
    }

    fun shoot(fromPlayerId: String?, toPlayerId: String?) {
        if (gameController?.state == GameState.PLAYER_TURN) {
            val realbullet =
                gameController?.gun?.chamber?.get(gameController?.gun?.currentChamber ?: 0)

            if (realbullet == 1) {
                val toPlayer = gameController?.players?.find { it.id == toPlayerId }
                val damage: Int = if (gameController?.gun?.doubleDamage == true) 2 else 1
                toPlayer?.currentHealth = (toPlayer.currentHealth ?: 1) - damage
                gameController?.gun?.doubleDamage = false
                if (toPlayer?.id == gameController?.turn){
                    gameController?.turn = if (toPlayer?.id == player1.id) player2.id else player1.id
                }

                if ((toPlayer?.currentHealth ?: 0) <= 0) {
                    val otherPlayer = gameController?.players?.find { it.id != toPlayerId }
                    otherPlayer?.points = (otherPlayer.points ?: 0) + 1
                    if (otherPlayer?.points == gameController?.winningPoints) {
                        gameController?.state = GameState.WINNER
                        gameController?.winner = otherPlayer?.name
                    }else {
                        gameController?.state = GameState.NEW_ROUND
                    }

                } else {
                    gameController?.state = GameState.PLAYER_TURN
                }

                gameController?.turn = if (fromPlayerId == player1.id) player2.id else player1.id
                if (toPlayer?.chainApplied == true) {
                    gameController?.turn = fromPlayerId
                    toPlayer.chainApplied = false
                }


            } else {
                if (fromPlayerId != toPlayerId) {
                    gameController?.turn = toPlayerId
                }
            }
            gameController?.gun?.currentChamber = (gameController?.gun?.currentChamber ?: 0) + 1
            val currentChamber = gameController?.gun?.currentChamber ?: 0
            val chamberSize = gameController?.gun?.chamber?.size ?: 0
            if (gameController?.state != GameState.WINNER && currentChamber == chamberSize) {
                gameController?.state = GameState.NEW_OBJECTS
            } else {
                gameController?.state = GameState.PLAYER_TURN_END
            }

        }
    }

    fun applyObject(fromPlayerId: String, toPlayerId: String, item: Object, position: Int) {

        val fromPlayer = gameController?.players?.find { it.id == fromPlayerId }
        val toPlayer = gameController?.players?.find { it.id == toPlayerId }
        val obj = fromPlayer?.objects?.first { it?.name == item.name }

        if (obj != null) {
            if (gameController?.adrenalineUsed == true) {
                gameController?.adrenalineUsed = false
            }
            when (obj.name) {
                ObjectNames.BEER -> {
                    gameController?.gun?.currentChamber += 1
                }
                ObjectNames.MAGNIFYING_GLASS -> {
                    gameController?.hint = if (gameController?.gun?.chamber?.get(
                            gameController?.gun?.currentChamber ?: 0
                        ) == 1
                    ) "Next bullet is real" else "Next bullet is fake"
                }
                ObjectNames.CIGARETTES -> {
                    toPlayer?.currentHealth = (toPlayer.currentHealth ?: 0) + 1
                }
                ObjectNames.HANDSAW -> {
                    gameController?.gun?.doubleDamage = true
                }
                ObjectNames.HANDCUFFS -> {
                    toPlayer?.chainApplied = true
                }
                ObjectNames.INVERTER -> {
                    val newChamber = mutableListOf<Int>()
                    newChamber.addAll(gameController?.gun?.chamber ?: listOf())
                    val currentChamberIndex = gameController?.gun?.currentChamber ?: 0
                    val newChamberValue = if (gameController?.gun?.chamber?.get(currentChamberIndex) == 1) 0 else 1
                    newChamber[currentChamberIndex] = newChamberValue
                    gameController?.gun?.chamber = newChamber

                }
                ObjectNames.EXPIRED_MEDICINE -> {
                    val restore = if (Random.nextInt(0, 2) == 0) -1 else 2
                    toPlayer?.currentHealth = (toPlayer.currentHealth ?: 0) + restore

                }
                ObjectNames.ADRENALINE -> {
                    gameController?.adrenalineUsed = true
                }
                ObjectNames.PHONE -> {
                    val randomPosition = Random.nextInt(gameController?.gun?.currentChamber?: 0,
                        gameController?.gun?.chamber?.size?.minus(1) ?: 0
                    )
                    val randomChamberValue = if (gameController?.gun?.chamber?.get(randomPosition) == 1) "real" else "fake"
                    gameController?.hint = "Bullet at position ${randomPosition + 1} is $randomChamberValue"
                }
            }

            fromPlayer.objects[position] = null

        }


    }
    fun clearHint() {
        gameController?.hint = ""
    }

    fun changeState(gameState: GameState): GameModel {
        gameController?.state = gameState
        return this.copy(gameController = this.gameController)
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        var result = gameController?.hashCode() ?: 0
        result = 31 * result + player1.hashCode()
        result = 31 * result + player2.hashCode()
        return result
    }

}


