package geekbrains.ru.translator.view.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import geekbrains.ru.translator.R
import geekbrains.ru.translator.constants.Constants.Companion.DATA_MODEL
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.ui.AlertDialogFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val TAG = "33333"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataModel = intent.extras?.getParcelable<DataModel>(DATA_MODEL)

        tv_text.text =  dataModel?.text?:""
        tv_transcription.text = dataModel?.meanings?.get(0)?.transcription?:""
        tv_translation.text = dataModel?.meanings?.get(0)?.translation?.text?:""

        val soundUrl = dataModel?.meanings?.get(0)?.soundUrl
        Log.d(TAG, "DetailActivity onCreate soundUrl = $soundUrl")
        val imageUrl = dataModel?.meanings?.get(0)?.imageUrl
        Log.d(TAG, "DetailActivity onCreate imageUrl = $imageUrl")

        imageUrl?. let{
            Picasso.get()
                .load("https:$it")
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(imageView)
        }

        buttonSound.setOnClickListener {
            soundUrl?. let{
                videoView_sound.setVideoPath("https:$it")
                videoView_sound.start()
            }
        }

    }

}