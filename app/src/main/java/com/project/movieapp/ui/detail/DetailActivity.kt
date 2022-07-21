package com.project.movieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.project.movieapp.BASE_URL_API_IMAGE
import com.project.movieapp.POSTER_SIZE_W780
import com.project.movieapp.R
import com.project.movieapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityDetailBinding
    
    private lateinit var viewModel: DetailViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail"
        
        //init
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        
        loadMovie()
    }

    private fun loadMovie() {
        
        showProgressBar(true)
        
        intent?.let { 
            val id = it.extras?.getString(EXTRA_DATA_ID)
            
            viewModel.getMovie(this, id.toString()).observe(this){ movie ->
                if(movie != null){
                    binding.apply { 
                        Glide.with(this@DetailActivity)
                            .load("$BASE_URL_API_IMAGE$POSTER_SIZE_W780${movie.poster}")
                            .placeholder(android.R.color.darker_gray)
                            .into(imgMovie)
                        
                        tvTitle.text = movie.name.toString()
                        tvDesc.text = movie.desc.toString()
                    }
                    showProgressBar(false)
                }else{
                    Toast.makeText(this, getString(R.string.failed_to_load), Toast.LENGTH_SHORT).show()
                    showProgressBar(false)
                }
            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        binding.apply { 
            if (state){
                imgMovie.visibility = View.GONE
                tvTitle.visibility = View.GONE
                tvDesc.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }else{
                imgMovie.visibility = View.VISIBLE
                tvTitle.visibility = View.VISIBLE
                tvDesc.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }

    companion object{
        const val EXTRA_DATA_ID = "extra_data_id"
    }
}