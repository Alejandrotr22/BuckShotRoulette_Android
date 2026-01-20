package es.ullr.buckshot.data.models

data class Player(
    val id: String = "",
    val name: String? = "",
    var maxHealth: Int? = 0,
    var currentHealth: Int? = 0,
    var objects: Array<Object?> = arrayOfNulls<Object>(6),
    var points: Int? = 0,
    var chainApplied: Boolean = false,
) {

    override fun equals(other: Any?): Boolean {
        return other === this
    }

    override fun hashCode(): Int {
        var result = maxHealth ?: 0
        result = 31 * result + (currentHealth ?: 0)
        result = 31 * result + (points ?: 0)
        result = 31 * result + chainApplied.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + objects.contentHashCode()
        return result
    }

}
