package es.ullr.buckshot.data.models

data class Gun(
    val total: Int = 6,
    val realBullets: Int = 3,
    var chamber: List<Int> = listOf(),
    var currentChamber: Int = 0,
    var doubleDamage: Boolean = false
)
