package com.project.movieapp.ui.detail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.movieapp.api.ApiClient
import com.project.movieapp.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val movie = MutableLiveData<MovieResponse>()

    fun getMovie(context: Context, id: String): LiveData<MovieResponse>{
        ApiClient.instance.getMovieById(id.toInt())
            .enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if(response.isSuccessful) movie.postValue(response.body())
                    else Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        return movie
    }
}