package com.matteomacri.gnam.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matteomacri.gnam.DetailRecipesActivity
import com.matteomacri.gnam.databinding.ItemRecipeBinding
import com.matteomacri.gnam.models.similarrecipes.SimilarRecipesItem

private var IMAGE = ""

class SimilarRecipesAdapter(val context: Context, val similarRecipesList: List<SimilarRecipesItem>):
RecyclerView.Adapter<SimilarRecipesAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                val intent = Intent(context, DetailRecipesActivity::class.java)
                    .putExtra("ID", similarRecipesList[position].id)
                    .putExtra("TITLE", similarRecipesList[position].title)
                    .putExtra("IMAGE", IMAGE)
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = similarRecipesList[position].title
            IMAGE = "https://spoonacular.com/recipeImages/"+similarRecipesList[position].id+"-556x370.jpg"
            Glide.with(context)
                .load(IMAGE)
                .into(ivThumbnail)
        }
    }

    override fun getItemCount() = similarRecipesList.size
}