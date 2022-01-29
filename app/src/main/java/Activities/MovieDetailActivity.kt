package Activities

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.bumptech.glide.Glide
import com.thex.nitv_test.R
import com.thex.nitv_test.databinding.ActivityMovieDetailBinding
import android.widget.Toast
import kotlin.concurrent.thread
import android.app.Activity
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent


class MovieDetailActivity : AppCompatActivity() {
    private var MovieTitle: String = ""
    private var MovieOverview: String = ""
    private var ReleaseDate: String = ""
    private var PosterPath: String = ""
    private var MoviePopularity: Double = 0.0
    private var VoteAverage: Double = 0.0
    private val URL: String = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"
    lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*Code starts here */

        //fetch extras from intent coming from recyclerview(Movie activity)
        if (intent.hasExtra("Title")) {
            MovieTitle = intent.getStringExtra("Title").toString()
            MovieOverview = intent.getStringExtra("Overview").toString()
            ReleaseDate = intent.getStringExtra("ReleaseDate").toString()
            PosterPath = intent.getStringExtra("Poster").toString()
            MoviePopularity = intent.getDoubleExtra("Popularity", 0.0)
            VoteAverage = intent.getDoubleExtra("VoteAverage", 0.0)

        }

        setData()


        //start video playing process in new Thread with runOnUIThread to push updated to UI
        val t1 = Thread(
            Runnable {
                runOnUiThread(
                    Runnable {
                        videoPlayer()
                    }
                )

            }
        )

        t1.start()

    }

    private fun setData() {
        binding.movieDetailHeader.tvHeaderText.text = MovieTitle
        Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185$PosterPath")
            .placeholder(R.drawable.imgplaceholder).into(binding.imgPosterImageDetail)
        binding.tvMovieTitleDetail.text = MovieTitle
        binding.tvMovieOverview.text = MovieOverview
        binding.tvMovieOverview.movementMethod = ScrollingMovementMethod()

        binding.tvMovieReleaseDate.text = ReleaseDate
        binding.tvMoviePopularity.text = MoviePopularity.toString()
        binding.tvMovieVote.text = VoteAverage.toString()
    }

    private fun videoPlayer() {     //function to play video through URL to videoview

        var mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoPlayer)
        binding.videoPlayer.setMediaController(mediaController)
        var uri = Uri.parse(URL)
        binding.videoPlayer.setVideoURI(uri)
        binding.videoPlayer.requestFocus()
        binding.videoPlayer.setZOrderOnTop(true)


        binding.videoPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {

            binding.videoPlayer.start()


        })
        binding.videoPlayer.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            Toast.makeText(
                applicationContext,
                "Sorry Error occured while playing video..!!!",
                Toast.LENGTH_LONG
            ).show()
            false
        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}