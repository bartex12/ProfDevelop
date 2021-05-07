package geekbrains.ru.translator.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bartex.core2.BaseActivity.Companion.DIALOG_FRAGMENT_TAG
import com.bartex.utils.network.ui.AlertDialogFragment
import com.bartex.utils.network.ui.OnlineLiveData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import geekbrains.ru.translator.R
import geekbrains.ru.translator.constants.Constants.Companion.DESCRIPTION_EXTRA
import geekbrains.ru.translator.constants.Constants.Companion.SOUND_EXTRA
import geekbrains.ru.translator.constants.Constants.Companion.TRANSCRIPTION_EXTRA
import geekbrains.ru.translator.constants.Constants.Companion.URL_EXTRA
import geekbrains.ru.translator.constants.Constants.Companion.WORD_EXTRA
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val TAG = "33333"

        fun getIntent(
            context: Context,
            word: String,
            transcription: String,
            description: String,
            url: String?,
            soundUrl: String?
        ): Intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(TRANSCRIPTION_EXTRA, transcription)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
            putExtra(SOUND_EXTRA, soundUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        description_swipe.setOnRefreshListener {
            startLoadingOrShowError()
        }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(this@DetailActivity,
        Observer {
            if (it) {
                setData()
            } else {
                AlertDialogFragment.newInstance(
                    getString(R.string.dialog_title_device_is_offline),
                    getString(R.string.dialog_message_device_is_offline)
                ).show( supportFragmentManager,  DIALOG_FRAGMENT_TAG )
                stopRefreshAnimationIfNeeded()
            }
        })
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_swipe.isRefreshing) {
            description_swipe.isRefreshing = false
        }
    }

    private fun setData() {

        val bundle = intent.extras

        tv_text.text =  bundle?.getString(WORD_EXTRA)
        tv_transcription.text = bundle?.getString(TRANSCRIPTION_EXTRA)
        tv_translation.text = bundle?.getString(DESCRIPTION_EXTRA)
        val soundUrl =  bundle?.getString(SOUND_EXTRA)
        Log.d(TAG, "DetailActivity onCreate soundUrl = $soundUrl")
        val imageUrl =  bundle?.getString(URL_EXTRA)
        Log.d(TAG, "DetailActivity onCreate imageUrl = $imageUrl")

        if (imageUrl.isNullOrBlank()){
            stopRefreshAnimationIfNeeded()
        }else{
            Picasso.get()
                .load("https:$imageUrl")
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(imageView as ImageView?, object : Callback{ //просто для памяти, что есть колбэк
                    override fun onSuccess() {
                        stopRefreshAnimationIfNeeded()
                    }
                    override fun onError(e: Exception?) {
                        stopRefreshAnimationIfNeeded()
                    }
                })
        }

        buttonSound.setOnClickListener {
            soundUrl?. let{
                videoView_sound.setVideoPath("https:$it")
                videoView_sound.start()
            }
        }
    }


}