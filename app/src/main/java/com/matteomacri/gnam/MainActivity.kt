package com.matteomacri.gnam

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.matteomacri.gnam.adapters.RecipesAdapter
import com.matteomacri.gnam.databinding.ActivityDetailRecipesBinding
import com.matteomacri.gnam.databinding.ActivityMainBinding
import com.matteomacri.gnam.models.Recipe
import com.matteomacri.gnam.services.RecipesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val recipesList = mutableListOf<Recipe>()

    lateinit var toggle: ActionBarDrawerToggle
    var drawerLayout: DrawerLayout? = null
    var supportActionBar: ActionBar? = null
    var navView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miItem1 -> Toast.makeText(this, "Click 1", Toast.LENGTH_SHORT).show()
                R.id.miItem2 -> Toast.makeText(this, "Click 2", Toast.LENGTH_SHORT).show()
                R.id.miItem3 -> Toast.makeText(this, "Click 3", Toast.LENGTH_SHORT).show()
            }
            true
        }

        fetchRecipes(binding)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_drawer_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
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