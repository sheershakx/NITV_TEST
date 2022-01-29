package RetrofitFiles

import Models.MovieModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Query

interface MovieAPI {

/* Retrofit call with variable URL to fetch movies list */
    @POST("3/movie/now_playing")
    fun getMoviesData(
        @Query("api_key") api_key: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Call<MovieModel>?

    companion object {
        var BASE_URL = "https://api.themoviedb.org/"

        fun create(): MovieAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(MovieAPI::class.java)

        }

    }


}