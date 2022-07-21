package com.project.movieapp.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.project.movieapp.response.MovieResponse

class DivMovieCallback(private val oldMovies: List<MovieResponse>, private val newMovies: List<MovieResponse>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldMovies.size

    override fun getNewListSize(): Int = newMovies.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldMovies[oldItemPosition].id == newMovies[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldMovies[oldItemPosition].id == newMovies[newItemPosition].id
}