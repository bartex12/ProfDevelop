package geekbrains.ru.translator.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartex.core2.BaseActivity
import com.bartex.utils.network.ui.isOnline
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.translator.R
import geekbrains.ru.translator.constants.Constants.Companion.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
import geekbrains.ru.translator.constants.Constants.Companion.HISTORY_ACTIVITY_FEATURE_NAME
import geekbrains.ru.translator.constants.Constants.Companion.HISTORY_ACTIVITY_PATH
import geekbrains.ru.translator.koin.injectDependencies
import geekbrains.ru.translator.view.detail.DetailActivity
import geekbrains.ru.translator.view.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity() : BaseActivity<AppState, MainInteractor>() {

    // Теперь ViewModel инициализируется через функцию by viewModel()
    // Это функция, предоставляемая Koin из коробки
    //c Koin так просто происходит инжекция viewModel
    override  lateinit var model: MainViewModel
    private lateinit var splitInstallManager: SplitInstallManager
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                startActivity(
                    DetailActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        data.meanings?.get(0)?.transcription?:"",
                        data.meanings?.get(0)?.translation?.translation?:"",
                        data.meanings?.get(0)?.imageUrl,
                        data.meanings?.get(0)?.soundUrl
                )
              )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initViews ()
    }

    private fun initViewModel() {
        check(main_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        //подписываемся на изменение данных
        model.getResult().observe(this, Observer { renderData(it) })
    }

    private fun initViews () {
        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
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
                //создаём SplitInstallManager
                splitInstallManager = SplitInstallManagerFactory.create(applicationContext)
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_ACTIVITY_FEATURE_NAME)
                        .build()

                //добавляем слушатели в случае ok и ошибки
                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        //ещё один способ отправить интент, но нужно самим следить за HISTORY_ACTIVITY_PATH
                        val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
