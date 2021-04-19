package geekbrains.ru.translator.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import geekbrains.ru.translator.R
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.ui.AlertDialogFragment

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"
    }

    abstract val model:ViewModel
    abstract  fun renderData(appState: AppState)

    protected var isNetworkAvailable: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //метод из NetUtils
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }
}
