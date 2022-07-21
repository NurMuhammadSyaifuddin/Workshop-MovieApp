package com.project.movieapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.movieapp.R
import com.project.movieapp.databinding.ActivityMainBinding
import com.project.movieapp.response.MovieResponse
import com.project.movieapp.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Movie App"

        //init
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        adapter = MainAdapter().apply {
            onClick {
                Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_DATA_ID, it)
                    startActivity(intent)
                }
            }
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.apply {
            showProgressBar(true)

            viewModel.getMovies(this@MainActivity).observe(this@MainActivity) { movies ->
                if (movies != null) {
                    adapter.movies = movies as MutableList<MovieResponse>
                    rvMovies.adapter = adapter
                    rvMovies.setHasFixedSize(true)
                    showProgressBar(false)
                } else {
                    Toast.makeText(this@MainActivity, R.string.data_not_found, Toast.LENGTH_SHORT)
                        .show()
                    showProgressBar(false)
                }

            }

        }
    }

    private fun showProgressBar(state: Boolean) {
        binding.apply {
            if (state) {
                rvMovies.visibility = View.GONE
                loading.visibility = View.VISIBLE
            } else {
                rvMovies.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }
}