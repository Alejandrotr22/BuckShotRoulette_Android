package es.ullr.buckshot.data.models

import buckshotroullete.composeapp.generated.resources.Res
import buckshotroullete.composeapp.generated.resources.adrenaline
import buckshotroullete.composeapp.generated.resources.beer
import buckshotroullete.composeapp.generated.resources.burner_phone
import buckshotroullete.composeapp.generated.resources.cigarettes
import buckshotroullete.composeapp.generated.resources.expired_medicine
import buckshotroullete.composeapp.generated.resources.handcuffs
import buckshotroullete.composeapp.generated.resources.handsaw
import buckshotroullete.composeapp.generated.resources.inverter
import buckshotroullete.composeapp.generated.resources.magnifying_glass
import org.jetbrains.compose.resources.DrawableResource

data class Object(val id: Int, val name: ObjectNames, val icon: DrawableResource, val position: Int = 0)

object Objects {
    val beer = Object(1, ObjectNames.BEER, Res.drawable.beer)
    val magnifyingGlass = Object(2, ObjectNames.MAGNIFYING_GLASS, Res.drawable.magnifying_glass)
    val cigarettes = Object(3, ObjectNames.CIGARETTES, Res.drawable.cigarettes)
    val handsaw = Object(4, ObjectNames.HANDSAW, Res.drawable.handsaw)
    val handcuffs = Object(5, ObjectNames.HANDCUFFS, Res.drawable.handcuffs)
    val inverter = Object(6, ObjectNames.INVERTER, Res.drawable.inverter)
    val expiredMedicine = Object(7, ObjectNames.EXPIRED_MEDICINE, Res.drawable.expired_medicine)
    val adrenaline = Object(8, ObjectNames.ADRENALINE, Res.drawable.adrenaline)
    val phone = Object(9, ObjectNames.PHONE, Res.drawable.burner_phone)

    val all = listOf(
        beer,
        magnifyingGlass,
        cigarettes,
        handsaw,
        handcuffs,
        inverter,
        expiredMedicine,
        adrenaline,
        phone
    )
}

enum class ObjectNames(val displayName: String) {
    BEER("Beer"),
    MAGNIFYING_GLASS("Magnifying Glass"),
    CIGARETTES("Cigarettes"),
    HANDSAW("Hand Saw"),
    HANDCUFFS("Handcuffs"),
    INVERTER("Inverter"),
    EXPIRED_MEDICINE("Expired Medicine"),
    ADRENALINE("Adrenaline"),
    PHONE("Phone");
}
