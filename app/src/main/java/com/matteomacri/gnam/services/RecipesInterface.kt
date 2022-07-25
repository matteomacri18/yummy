package com.matteomacri.gnam.services

import com.matteomacri.gnam.models.Recipes
import com.matteomacri.gnam.models.ingredients.Ingredients
import com.matteomacri.gnam.models.similarrecipes.SimilarRecipes
import com.matteomacri.gnam.models.similarrecipes.SimilarRecipesItem
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesInterface {
    @GET("/recipes/complexSearch?apiKey=f3f302468e2d4d88a5796884819ed9d5")
    suspend fun getSearchRecipes(): Recipes

    @GET("/recipes/{id}/information?apiKey=f3f302468e2d4d88a5796884819ed9d5")
    suspend fun getRecipeInformation(@Path("id") id: String): Ingredients

    @GET("/recipes/{id}/similar?apiKey=f3f302468e2d4d88a5796884819ed9d5&number=5")
    suspend fun getSimilarRecipes(@Path("id") id: String): List<SimilarRecipesItem>

}