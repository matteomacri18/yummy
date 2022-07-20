package com.matteomacri.gnam.models

import com.google.gson.annotations.SerializedName

data class Recipes(
    val number: Int,
    val offset: Int,
    @SerializedName("results")
    val recipes: List<Recipe>,
    val totalResults: Int
)