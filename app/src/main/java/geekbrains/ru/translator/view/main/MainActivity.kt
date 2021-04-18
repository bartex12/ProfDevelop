package geekbrains.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import geekbrains.ru.translator.R
import geekbrains.ru.translator.constants.Constants.Companion.DATA_MODEL
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.view.base.BaseActivity
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import geekbrains.ru.translator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : BaseActivity<AppState>() {

    override val model: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private var adapter: MainAdapter? = null

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {

                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DATA_MODEL, data)
                startActivity(intent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_fab.setOnClickListener {

            val searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
                        .observe(this@MainActivity, Observer {renderData(it)})
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    // Переопределяем базовый метод
    override fun renderData(appState: AppState) {
        // В зависимости от состояния модели данных (загрузка, отображение,
        // ошибка) отображаем соответствующий экран
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                        main_activity_recyclerview.adapter = MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                // Задел на будущее, если понадобится отображать прогресс
                // загрузки
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = VISIBLE
                    progress_bar_round.visibility = GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = GONE
                    progress_bar_round.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        error_textview.text = error ?: getString(R.string.undefined_error)
        reload_button.setOnClickListener {
            model.getData("hi", true)
                .observe(this@MainActivity, Observer {renderData(it)})
        }
    }

    private fun showViewSuccess() {
        success_linear_layout.visibility = VISIBLE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = GONE
    }

    private fun showViewLoading() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = VISIBLE
        error_linear_layout.visibility = GONE
    }

    private fun showViewError() {
        success_linear_layout.visibility = GONE
        loading_frame_layout.visibility = GONE
        error_linear_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG_1"
    }
}
