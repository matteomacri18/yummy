package com.matteomacri.gnam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.matteomacri.gnam.adapters.SimilarRecipesAdapter
import com.matteomacri.gnam.databinding.ActivityDetailRecipesBinding
import com.matteomacri.gnam.models.ingredients.ExtendedIngredient
import com.matteomacri.gnam.models.ingredients.Ingredients
import com.matteomacri.gnam.models.similarrecipes.SimilarRecipesItem
import com.matteomacri.gnam.services.RecipesApiService
import kotlinx.coroutines.*

private const val TAG = "DetailRecipesActivity"

class DetailRecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecipesBinding
    private val ingredientsList = mutableListOf<ExtendedIngredient>()
    private var preparation = ""

    private val similarRecipesList = mutableListOf<SimilarRecipesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchIngredients(binding)

    }


    private fun fetchIngredients(binding: ActivityDetailRecipesBinding) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id = intent.getIntExtra("ID", 0).toString()
                val title = intent.getStringExtra("TITLE")
                val image = intent.getStringExtra("IMAGE")

                var recipeInformation : Ingredients ?= null
                async {
                    val response = RecipesApiService.getRetrofitClient()
                    val _recipeinformation = response.getRecipeInformation(id)
                    for (ingredient in _recipeinformation.extendedIngredients) {
                        Log.i(TAG, ingredient.toString())
                        addIngredientToList(ingredient)
                    }
                    recipeInformation = _recipeinformation
                }.await()

                // call to the second APIs, async because the first will be cutted out when started of the
                // second one
                async {
                    val response = RecipesApiService.getRetrofitClient()
                    val _similarRecipes = response.getSimilarRecipes(id)
                    for (recipe in _similarRecipes){
                        Log.i(TAG, recipe.toString())
                        addSimilarRecipesToList(recipe)
                    }
                }.await()

                withContext(Dispatchers.Main) {
                    binding.apply {
                        tvTitle.text = title
                        Glide.with(this@DetailRecipesActivity)
                            .load(image)
                            .into(ivRecipe)
                        tvPreparation.text = preparation
                        tvHealthScore.text = recipeInformation?.healthScore.toString()
                    }

                    setRecyclerView(binding)
                }

            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun setRecyclerView(binding: ActivityDetailRecipesBinding) {
        binding.rvSimilarRecipes.apply {
            layoutManager = LinearLayoutManager(
                this@DetailRecipesActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = SimilarRecipesAdapter(this@DetailRecipesActivity, similarRecipesList)
        }
    }

    private fun addSimilarRecipesToList(similarRecipes: SimilarRecipesItem) {
        similarRecipesList.add(similarRecipes)
    }

    private fun addIngredientToList(ingredient: ExtendedIngredient) {
        ingredientsList.add(ingredient)
        preparation += "- " + ingredient.original + "\n"
    }
}