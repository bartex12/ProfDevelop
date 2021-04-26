package geekbrains.ru.translator.view.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import geekbrains.ru.translator.R
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.interactor.Interactor
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.utils.ui.AlertDialogFragment
import kotlinx.android.synthetic.main.loading_layout.*

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity() {

    companion object {
       const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"
    }

    abstract val model:ViewModel
    //abstract  fun renderData(appState: AppState)

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

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewSuccess()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = View.GONE
                    progress_bar_round.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewSuccess()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
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

    private fun showViewSuccess() {
        //success_linear_layout.visibility = View.VISIBLE
        loading_frame_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        //success_linear_layout.visibility = View.GONE
        loading_frame_layout.visibility = View.VISIBLE
    }

    abstract fun setDataToAdapter(data: List<DataModel>)
}
