package com.matteomacri.gnam.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matteomacri.gnam.DetailRecipesActivity
import com.matteomacri.gnam.databinding.ItemRecipeBinding
import com.matteomacri.gnam.models.Recipe
import com.matteomacri.gnam.models.ingredients.ExtendedIngredient
import com.matteomacri.gnam.models.similarrecipes.SimilarRecipesItem

class RecipesAdapter(val context: Context, val recipesList: List<Recipe>) :
    RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(context, DetailRecipesActivity::class.java)
                    .putExtra("ID", recipesList[position].id)
                    .putExtra("TITLE", recipesList[position].title)
                    .putExtra("IMAGE", recipesList[position].image)
                startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = recipesList[position].title
            Glide.with(context)
                .load(recipesList[position].image)
                .into(ivThumbnail)
        }
    }

    override fun getItemCount() = recipesList.size
}