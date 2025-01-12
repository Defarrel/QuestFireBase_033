package com.example.tugas_pertemuan14.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

object DestinasiInsert : DestinasiNavigasi {
    override val route = "insert"
    override val titleRes = "Insert"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    const val NIM = "nim"
    override val titleRes = "Detail"
    val routeWithArg = "$route/{$NIM}"
}