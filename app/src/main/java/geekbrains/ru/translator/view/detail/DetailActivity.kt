package geekbrains.ru.translator.view.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import geekbrains.ru.translator.R
import geekbrains.ru.translator.constants.Constants.Companion.DATA_MODEL
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.ui.AlertDialogFragment
import geekbrains.ru.translator.view.base.BaseActivity.Companion.DIALOG_FRAGMENT_TAG
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    companion object{
        const val TAG = "33333"
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
        if (isOnline(applicationContext)) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show( supportFragmentManager,  DIALOG_FRAGMENT_TAG )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_swipe.isRefreshing) {
            description_swipe.isRefreshing = false
        }
    }

    private fun setData() {
        val dataModel = intent.extras?.getParcelable<DataModel>(DATA_MODEL)

        tv_text.text =  dataModel?.text?:""
        tv_transcription.text = dataModel?.meanings?.get(0)?.transcription?:""
        tv_translation.text = dataModel?.meanings?.get(0)?.translation?.text?:""
        val soundUrl = dataModel?.meanings?.get(0)?.soundUrl
        Log.d(TAG, "DetailActivity onCreate soundUrl = $soundUrl")
        val imageUrl = dataModel?.meanings?.get(0)?.imageUrl
        Log.d(TAG, "DetailActivity onCreate imageUrl = $imageUrl")

        if (imageUrl.isNullOrBlank()){
            stopRefreshAnimationIfNeeded()
        }else{
            Picasso.get()
                .load("https:$imageUrl")
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(imageView, object : Callback{ //просто для памяти, что есть колбэк
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