package geekbrains.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import geekbrains.ru.translator.R
import geekbrains.ru.translator.constants.Constants.Companion.DATA_MODEL
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.utils.network.isOnline
import geekbrains.ru.translator.view.base.BaseActivity
import geekbrains.ru.translator.view.history.HistoryActivity
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import geekbrains.ru.translator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity() : BaseActivity<AppState, MainInteractor>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG_1"
    }

    // Теперь ViewModel инициализируется через функцию by viewModel()
    // Это функция, предоставляемая Koin из коробки
    //c Koin так просто происходит инжекция viewModel
    override  val model by viewModel<MainViewModel>()

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {

                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DATA_MODEL, data)
                startActivity(intent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        //TranslatorApp.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    //только делаем запрос без подписки
                    isNetworkAvailable = isOnline(applicationContext)
                    if (isNetworkAvailable) {
                        model.getData(searchWord, isNetworkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        //подписываемся на изменение данных
       model.getResult().observe(this, Observer {renderData(it)})

        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        main_activity_recyclerview.adapter = adapter
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
