package com.matteomacri.gnam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteomacri.gnam.adapters.RecipesAdapter
import com.matteomacri.gnam.databinding.ActivityMainBinding
import com.matteomacri.gnam.models.Recipe
import com.matteomacri.gnam.models.ingredients.ExtendedIngredient
import com.matteomacri.gnam.services.RecipesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val recipesList = mutableListOf<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchRecipes(binding)
    }


    private fun fetchRecipes(binding: ActivityMainBinding) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RecipesApiService.getRetrofitClient()
                val searchRepices = response.getSearchRecipes()
                for (recipe in searchRepices.recipes) {
                    Log.i(TAG, recipe.toString())
                    addRecipeToList(recipe)
                }
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.VISIBLE
                    setRecyclerView(binding)
                    binding.progressBar.visibility = View.GONE
                }


            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }


    private fun setRecyclerView(binding: ActivityMainBinding) {
        binding.rvRecipes.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = RecipesAdapter(this@MainActivity, recipesList)
        }
    }


    private fun addRecipeToList(recipe: Recipe) = recipesList.add(recipe)

}