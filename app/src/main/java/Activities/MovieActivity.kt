package Activities

import Adapters.movieInfoAdapter
import LocalStorage.DatabaseHelper
import Models.MovieModel
import RetrofitFiles.MovieAPI
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.thex.nitv_test.databinding.ActivityMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        binding.rvMovieList.layoutManager = GridLayoutManager(applicationContext, 3)

       if ( !CheckInternet().checkForInternet(applicationContext))
        {
            Toast.makeText(applicationContext, "Please Check Internet Connection !!", Toast.LENGTH_SHORT).show()
        }
        //fetch data from retrofit || localDB
        getLocalRecord()
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
                    getLocalRecord()

                    return
                }

                //assigning data to adapter and passing adapter to recyclerview

                var moviedata: MovieModel = response.body()!!

                val adapter = movieInfoAdapter(moviedata)
                binding.rvMovieList.adapter = adapter
                saveLocalRecord(moviedata)


            }


            override fun onFailure(call: Call<MovieModel>?, t: Throwable?) {
                Log.e(TAG, "onFailure: " + t?.message)
                getLocalRecord()

            }
        })
    }

    fun saveLocalRecord(movieModel: MovieModel) {

        val db: SQLiteDatabase? = DatabaseHelper(this).writableDatabase
        val databaseHelper: DatabaseHelper = DatabaseHelper(this)
        databaseHelper.deleteMovies()
        databaseHelper.addMovies(movieModel)

    }

    fun getLocalRecord() {
        val databaseHelper: DatabaseHelper = DatabaseHelper(this)
        var movieDetails: List<MovieModel.MovieOtherDetails> = databaseHelper.viewMovies()
        var movieModel: MovieModel = MovieModel(movieDetails)
        val adapter = movieInfoAdapter(movieModel)
        binding.rvMovieList.adapter = adapter


    }
}





