package Models

import com.google.gson.annotations.SerializedName
/*
* Movie Model that holds data of movie rendered through json  fetched by retrofit library
*  */
data class MovieModel(
    @SerializedName("results")
    val Result: List<MovieOtherDetails>,

    ) {


    data class MovieOtherDetails(
        @SerializedName("original_title")
        val MovieTitle: String,
        @SerializedName("overview")
        val MovieOverview: String,
        @SerializedName("poster_path")
        val PosterPath: String,

        @SerializedName("popularity")
        val Popularity: Double,
        @SerializedName("release_date")
        val ReleaseDate: String,
        @SerializedName("vote_average")
        val VoteAverage: Double
    ) {

    }
}