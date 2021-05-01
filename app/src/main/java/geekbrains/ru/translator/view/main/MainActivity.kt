package geekbrains.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartex.core2.BaseActivity
import com.bartex.historyscreen.HistoryActivity
import com.bartex.utils.network.ui.isOnline
import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.translator.R
import geekbrains.ru.translator.view.detail.DetailActivity
import geekbrains.ru.translator.view.main.adapter.MainAdapter
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

                startActivity(DetailActivity.getIntent(
                    this@MainActivity,
                    data.text!!,
                    data.meanings?.get(0)?.transcription?:"",
                    data.meanings?.get(0)?.translation?.translation?:"",
                    data.meanings?.get(0)?.imageUrl,
                    data.meanings?.get(0)?.soundUrl
                ))
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                intent.putExtra(DATA_MODEL, data)
//                startActivity(intent)
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
