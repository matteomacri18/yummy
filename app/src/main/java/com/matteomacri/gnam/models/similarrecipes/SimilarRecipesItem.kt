package com.matteomacri.gnam.models.similarrecipes

data class SimilarRecipesItem(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)