package com.example.pokeapirecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokeAdapter (private val pokeList: List<Pokemon>):RecyclerView.Adapter<PokeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView
        val pokeName: TextView
        val pokeType: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            pokeImage = view.findViewById(R.id.pokemonImage)
            pokeName = view.findViewById(R.id.poke_name)
            pokeType = view.findViewById(R.id.poke_type)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = pokeList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageData = pokeList[position]
        Glide.with(holder.itemView.context)
            .load(imageData.pokeImageUrl)
            .centerCrop()
            .into(holder.pokeImage)
        holder.pokeName.text = imageData.pokeName
        holder.pokeType.text = imageData.pokeType
    }
}