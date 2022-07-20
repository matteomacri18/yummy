package com.matteomacri.gnam.models.ingredients

data class WinePairing(
    val pairedWines: List<Any>,
    val pairingText: String,
    val productMatches: List<Any>
)