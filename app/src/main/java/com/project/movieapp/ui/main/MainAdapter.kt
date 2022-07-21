package com.project.movieapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.movieapp.BASE_URL_API_IMAGE
import com.project.movieapp.POSTER_SIZE_W780
import com.project.movieapp.databinding.ItemMovieBinding
import com.project.movieapp.response.MovieResponse

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var listener: ((String) -> Unit)? = null

    var movies = mutableListOf<MovieResponse>()
    set(value) {
        val callback = DivMovieCallback(field, value)
        val result = DiffUtil.calculateDiff(callback)
        field.clear()
        field.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResponse){
            binding.apply {
                Glide.with(itemView.context)
                    .load("$BASE_URL_API_IMAGE$POSTER_SIZE_W780${movie.poster}")
                    .placeholder(android.R.color.darker_gray)
                    .into(imgPoster)

                tvTitle.text = movie.name.toString()
                tvDesc.text = movie.desc.toString()

                listener?.let {
                    itemView.setOnClickListener {
                        it(movie.id.toString())
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun onClick(listener: ((String) -> Unit)?){
        this.listener = listener
    }
}