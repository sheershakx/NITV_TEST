package Activities

import Adapters.movieInfoAdapter
import Models.MovieModel
import RetrofitFiles.MovieAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thex.nitv_test.R
import com.thex.nitv_test.databinding.ActivityMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MovieActivity : AppCompatActivity() {
    private var api_key: String = "80dff2970093b6849866a98cc4bf6f34"
    private var language: String = "en-US"
    private var page: Int = 1
    private val TAG: String = "MovieActivityLOG"
    private lateinit var binding: ActivityMovieBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /* Start your code here */
        //set header text of activity through layout  header
        binding.headerlayout.tvHeaderText.text = "Movies List"

        //fetch data from retrofit
        fetchData()


    }

    private fun fetchData() {
        //calling retrofit fun to fetch movie data
        val movieAPI = MovieAPI.create().getMoviesData(api_key, language, page)
        movieAPI?.enqueue(object : Callback<MovieModel> {
            override fun onResponse(
                call: Call<MovieModel>?,
                response: Response<MovieModel>?
            ) {

                if (!response!!.isSuccessful) {


                    Log.d(TAG, "onResponse: " + response.code())
                    return
                }

                //assigning data to adapter and passing adapter to recyclerview
                binding.rvMovieList.layoutManager = GridLayoutManager(applicationContext, 3)
                var moviedata: MovieModel = response.body()!!
                val adapter = movieInfoAdapter(moviedata)
                binding.rvMovieList.adapter = adapter


            }


            override fun onFailure(call: Call<MovieModel>?, t: Throwable?) {
                Log.e(TAG, "onFailure: " + t?.message)

            }
        })
    }
}





