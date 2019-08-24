package com.nandra.moviecatalogue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView

class ERecyclerViewAdapter(private val filmList : ArrayList<Film>) : RecyclerView.Adapter<ERecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_movie_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = filmList[position]
        holder.title.text = currentMovie.title
        holder.rating.text = currentMovie.rating
        holder.genre.text = currentMovie.genre
        Glide.with(holder.itemView)
            .load(currentMovie.poster)
            .apply(RequestOptions().override(200, 300))     //Optimizing Image Loading For Thumbnail
            .into(holder.img)
        holder.overview.text = currentMovie.overview
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.item_text_movie_title)
        val rating : TextView = view.findViewById(R.id.item_text_movie_rating)
        val genre : TextView = view.findViewById(R.id.item_text_movie_genre)
        val img : RoundedImageView = view.findViewById(R.id.item_image_movie_poster)
        val overview : TextView = view.findViewById(R.id.item_text_movie_overview)
    }

}