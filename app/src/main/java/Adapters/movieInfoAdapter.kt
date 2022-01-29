package Adapters

import Activities.MovieDetailActivity
import Activities.SplashActivity
import Models.MovieModel
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thex.nitv_test.R

class movieInfoAdapter(private val movieData: MovieModel) :
    RecyclerView.Adapter<movieInfoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.moviegridlayout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val movieList = movieData.Result[position]
        //set movie title in recyclerview layout file
        holder.titleView.text = movieList.MovieTitle
        //set poster image (through glide) in recyclerview layout file

        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + movieList.PosterPath)
            .placeholder(R.drawable.imgplaceholder)
            .into(holder.posterView)

        /*on click event when user clicks Poster Image to go through details page.
        * Includes Extras to pass datas into next activity
        * */

        holder.posterView.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("Title", movieList.MovieTitle)
            intent.putExtra("Poster", movieList.PosterPath)
            intent.putExtra("ReleaseDate", movieList.ReleaseDate)
            intent.putExtra("Popularity", movieList.Popularity)
            intent.putExtra("VoteAverage", movieList.VoteAverage)
            intent.putExtra("Overview", movieList.MovieOverview)
            //bundle for passing TransitionAnimaition to next activity
            var bundle: Bundle =
                ActivityOptions.makeSceneTransitionAnimation(context as Activity?).toBundle()
            context.startActivity(intent, bundle)

        })


    }

    override fun getItemCount(): Int {
        return movieData.Result.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //assigning views of recyclerview grid layout file
        val posterView: ImageView = itemView.findViewById(R.id.imgPosterImage)
        val titleView: TextView = itemView.findViewById(R.id.tvMovieTitle)

    }
}