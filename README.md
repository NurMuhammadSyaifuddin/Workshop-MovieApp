# Movie App

## Demo
https://user-images.githubusercontent.com/68520848/180180356-7bad0e86-3da2-404f-b667-a762d849d915.mp4

#### Buat project baru dan sesuaikan packagenya seperti gambar di bawah agar lebih terorganisisr
![image](https://user-images.githubusercontent.com/68520848/180181925-9ec5eb56-c1f9-41a0-968f-6bb85a59c71a.png)

#### Tambahkan library di build.gradle level module(:app)
```` Ruby
// Retrofit Dependency
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"

// Glide
implementation 'com.github.bumptech.glide:glide:4.13.1'
````

#### Untuk dapat mengakses data movie, maka kita perlu memiliki Api Key. Dapatkan Api Key di link berikut:
[https://www.themoviedb.org/settings/api]

#### Masuk ke gradle.properties(Project Properties) dan tambahkan Api Key yang sudah didapatkan serta tambahkan Base Url
```` Ruby
API_KEY="cb6b5c7268f2c64e55df8f887910158a"
BASE_URL = "https://api.themoviedb.org/3/"
````

#### Masuk ke build.gradle level module(:app). Di dalam blok defaultConfig {} tambahkan field baru agar dapat diakses pada kode program kita
```` Ruby
defaultConfig {
        applicationId "com.project.movieapp"
        minSdk 21
        targetSdk 32
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField "String", "API_KEY", API_KEY
        buildConfigField "String", "BASE_URL", BASE_URL
    }
````

#### Kemudian tekan Sync Now, jika sudah tekan button Make Project (ctrl+F9)
![image](https://user-images.githubusercontent.com/68520848/180189429-3f55c34c-bc6a-48a8-995a-4f0543c70c9d.png)

#### Ubah resource color.xml
```` Ruby
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#49A35A</color>
    <color name="colorPrimaryDark">#0C732F</color>

    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>

    <color name="gray">#eeeeee</color>

    <color name="placeholder">#6FB6B6B6</color>
</resources>
````

#### Ubah resource strings.xml
```` Ruby
<resources>
    <string name="app_name">Movie App</string>
    <string name="data_not_found">Data not found</string>
    <string name="failed_to_load">Failed to load</string>
</resources>
````

#### Ubah resource themes.xml
```` Ruby
<resources>
    <style name="Theme.MovieApp" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Primary brand color. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryVariant">@color/colorPrimaryDark</item>
        <item name="colorOnPrimary">@color/white</item>
        <item name="colorOnSecondary">@color/black</item>
        <!-- Status bar color. -->
        <item name="android:statusBarColor" >?attr/colorPrimaryVariant</item>
        <!-- Customize your theme here. -->
    </style>
</resources>
````

#### Tambahkan resource dimens.xml
```` Ruby
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="dp_0">0dp</dimen>
    <dimen name="dp_8">8dp</dimen>
    <dimen name="dp_4">4dp</dimen>
    <dimen name="dp_16">16dp</dimen>
    <dimen name="sp_16">16sp</dimen>
    <dimen name="dp_400">400dp</dimen>
    <dimen name="dp_64">64dp</dimen>
</resources>
````

#### Ubah kode pada activity_main
```` Ruby
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.main.MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_movies"
        android:layout_width="@dimen/dp_0"
        android:layout_height="@dimen/dp_0"
        android:clipToPadding="false"
        android:paddingHorizontal="@dimen/dp_8"
        android:paddingVertical="@dimen/dp_4"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        tools:listitem="@layout/item_movie"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <ProgressBar
        android:id="@+id/loading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        style="?attr/progressBarStyle"
        android:visibility="gone"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
````

#### Buat resource layout baru dengan nama item_movie.xml
```` Ruby
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    app:cardCornerRadius="@dimen/dp_16"
    app:cardElevation="@dimen/dp_8"
    app:cardMaxElevation="@dimen/dp_8"
    android:layout_margin="@dimen/dp_8">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/bg_item_click">

        <ImageView
            android:id="@+id/img_poster"
            android:layout_width="130dp"
            android:layout_height="150dp"
            tools:src="@color/gray"
            android:adjustViewBounds="true"
            android:scaleType="fitXY"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            tools:ignore="ContentDescription"/>

        <TextView
            android:id="@+id/tv_title"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            tools:text="Naruto Shippudden"
            android:textSize="@dimen/sp_16"
            android:textColor="@color/black"
            android:layout_marginHorizontal="@dimen/dp_16"
            android:ellipsize="end"
            android:maxLines="2"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toEndOf="@id/img_poster"
            app:layout_constraintBottom_toTopOf="@id/tv_desc"
            app:layout_constraintVertical_chainStyle="packed"
            app:layout_constraintEnd_toEndOf="parent"/>

        <TextView
            android:id="@+id/tv_desc"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            tools:text="@tools:sample/lorem/random"
            android:maxLines="2"
            android:ellipsize="end"
            android:layout_marginHorizontal="@dimen/dp_16"
            android:layout_marginTop="@dimen/dp_8"
            app:layout_constraintTop_toBottomOf="@id/tv_title"
            app:layout_constraintStart_toEndOf="@id/img_poster"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"/>

    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.cardview.widget.CardView>
````

#### Tambahkan resource drawable baru dengan nama bg_item_click.xml
```` Ruby
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/gray">

    <item>
        <shape android:shape="rectangle">
            <corners android:radius="@dimen/dp_16"/>
            <solid android:color="@color/white"/>
        </shape>
    </item>

</ripple>
````

#### Buat activity baru dengan nama DetailActivity dan ubah kode pada activity_detail
```` Ruby
<?xml version="1.0" encoding="utf-8"?>
<androidx.core.widget.NestedScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:context=".ui.detail.DetailActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ImageView
            android:id="@+id/img_movie"
            android:layout_width="match_parent"
            android:layout_height="@dimen/dp_400"
            android:background="@android:color/darker_gray"
            android:scaleType="fitXY"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            tools:ignore="ContentDescription" />

        <TextView
            android:id="@+id/tv_title"
            android:layout_width="@dimen/dp_0"
            android:layout_height="wrap_content"
            android:layout_marginStart="10dp"
            android:textColor="@color/black"
            android:textSize="@dimen/sp_16"
            android:textStyle="bold"
            android:fontFamily="sans-serif-medium"
            android:layout_margin="@dimen/dp_16"
            app:layout_constraintTop_toBottomOf="@id/img_movie"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            tools:text="Naruto Shipudden" />

        <TextView
            android:id="@+id/tv_desc"
            android:layout_width="@dimen/dp_0"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:fontFamily="sans-serif-medium"
            android:lineSpacingExtra="@dimen/dp_4"
            app:layout_constraintTop_toBottomOf="@id/tv_title"
            app:layout_constraintStart_toStartOf="@id/tv_title"
            app:layout_constraintEnd_toEndOf="@id/tv_title"
            tools:text="@tools:sample/lorem/random" />

        <ProgressBar
            android:id="@+id/loading"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            style="?attr/progressBarStyle"
            android:layout_marginTop="@dimen/dp_64"
            android:visibility="gone"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"/>

    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.core.widget.NestedScrollView>
````

#### Pada package response tambahkan data class baru dengan nama ListResponse.kt
```` Ruby
package com.project.movieapp.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("results")
    val result: List<T>
)
````

#### Pada package response tambahkan data class baru dengan nama MovieResponse.kt
```` Ruby
package com.project.movieapp.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var name: String? = null,
    @SerializedName("overview")
    var desc: String? = null,
    @SerializedName("poster_path")
    var poster: String? = null,
    @SerializedName("backdrop_path")
    var imgPreview: String? = null
)
````

#### Pada package api buat interface baru dengan nama ApiService.kt
```` Ruby
package com.project.movieapp.api

import com.project.movieapp.BuildConfig.API_KEY
import com.project.movieapp.response.ListResponse
import com.project.movieapp.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/now_playing?api_key=$API_KEY")
    fun getMovies(): Call<ListResponse<MovieResponse>>

    @GET("movie/{movie_id}?api_key=$API_KEY")
    fun getMovieById(@Path("movie_id") id: Int): Call<MovieResponse>

}
````

#### Pada package api buat object baru dengan nama ApiClient.kt
```` Ruby
package com.project.movieapp.api

import com.project.movieapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val httpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val instance: ApiService = retrofit.build().create(ApiService::class.java)

}
````

#### Pada package main tambahkan class baru dengan nama MainViewModel.kt
```` Ruby
package com.project.movieapp.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.movieapp.api.ApiClient
import com.project.movieapp.response.ListResponse
import com.project.movieapp.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val movies = MutableLiveData<List<MovieResponse>>()

    fun getMovies(context: Context): LiveData<List<MovieResponse>>{
        ApiClient.instance.getMovies()
            .enqueue(object : Callback<ListResponse<MovieResponse>>{
                override fun onResponse(
                    call: Call<ListResponse<MovieResponse>>,
                    response: Response<ListResponse<MovieResponse>>
                ) {
                    if (response.isSuccessful) movies.postValue(response.body()?.result)
                    else Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ListResponse<MovieResponse>>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        return movies
    }
}
````

#### Pada package main tambahkan class baru dengan nama MainAdapter.kt
```` Ruby
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
````

#### Pada package main tambahkan class baru dengan nama DivMovieCallback.kt
```` Ruby
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
````

#### Pada package com.project.movieapp tambahkan file baru dengan nama Utils.kt
```` Ruby
package com.project.movieapp

const val BASE_URL_API_IMAGE = "https://image.tmdb.org/t/p/"
const val POSTER_SIZE_W780 = "w780"
````

#### Masuk ke class MainActivity.kt dan ubah kodenya seperti berikut
```` Ruby
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
````

#### Pada package detail tambahkan class baru dengan nama DetailViewModel.kt
```` Ruby
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
````

#### Masuk ke class DetailActivity.kt dan ubah kodenya seperti berikut
```` Ruby
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
````

#### Terakhir, jalankan aplikasi dan hasilnya akan sama seperti yang ada di demo
