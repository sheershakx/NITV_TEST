package LocalStorage

import Models.MovieModel
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "MoviesDatabase.db"
        private val TABLE_MOVIE = "MoviesData"
        private val TITLE = "title"
        private val OVERVIEW = "overview"
        private val POSTERPATH = "posterpath"
        private val VOTE_AVERAGE = "voteaverage"
        private val POPULARITY = "popularity"
        private val RELEASE_DATE = "releasedate"
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_MOVIE_TABLE = ("CREATE TABLE " + TABLE_MOVIE + "("
                + TITLE + " TEXT,"
                + OVERVIEW + " TEXT,"
                + POSTERPATH + " TEXT,"
                + VOTE_AVERAGE + " TEXT,"
                + POPULARITY + " TEXT,"
                + RELEASE_DATE + " TEXT" + ")")
        p0?.execSQL(CREATE_MOVIE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        onCreate(p0)
    }


    //functiond to insert data
    fun addMovies(movieData: MovieModel) {
        var movieListMain = movieData.Result

        for (movieList in movieListMain) {
            // var movieList = movieData.Result
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(TITLE, movieList.MovieTitle)
            contentValues.put(OVERVIEW, movieList.MovieOverview)
            contentValues.put(POSTERPATH, movieList.PosterPath)
            contentValues.put(POPULARITY, movieList.Popularity)
            contentValues.put(VOTE_AVERAGE, movieList.VoteAverage)
            contentValues.put(RELEASE_DATE, movieList.ReleaseDate)

            // Inserting Row
            db.insert(TABLE_MOVIE, null, contentValues)
            // Closing database connection
            db.close()
        }

        // return success
    }


    //method to read data
    @SuppressLint("Range")
    fun viewMovies(): List<MovieModel.MovieOtherDetails> {
        val movieList: ArrayList<MovieModel.MovieOtherDetails> =
            ArrayList<MovieModel.MovieOtherDetails>()
        val selectQuery = "SELECT  * FROM $TABLE_MOVIE"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var title: String
        var overview: String
        var posterpath: String
        var popularity: Double
        var voteaverage: Double
        var releasedate: String
        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(cursor.getColumnIndex(TITLE))
                overview = cursor.getString(cursor.getColumnIndex(OVERVIEW))
                posterpath = cursor.getString(cursor.getColumnIndex(POSTERPATH))
                popularity = cursor.getString(cursor.getColumnIndex(POPULARITY)).toDouble()
                voteaverage = cursor.getString(cursor.getColumnIndex(VOTE_AVERAGE)).toDouble()
                releasedate = cursor.getString(cursor.getColumnIndex(RELEASE_DATE))

                val movieData = MovieModel.MovieOtherDetails(
                    MovieTitle = title,
                    MovieOverview = overview,
                    PosterPath = posterpath,
                    Popularity = popularity,
                    ReleaseDate = releasedate,
                    VoteAverage = voteaverage
                )
                movieList.add(movieData)
            } while (cursor.moveToNext())
        }
        return movieList
    }

    fun deleteMovies(){
        val db = this.writableDatabase
        val contentValues = ContentValues()

     db.delete(TABLE_MOVIE,null,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection

    }

}