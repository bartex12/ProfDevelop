package com.bartex.historyscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.bartex.core2.BaseActivity
import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.DataModel
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }


    @SuppressLint("InlinedApi")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        check(history_activity_recyclerview.adapter == null) { "The ViewModel should be initialised first" }
        injectDependencies()
        // при использовании scope модель мы получаем через currentScope, остальное без изменений
        //Теперь граф зависимостей создаётся, когда он действительно нужен,
        // и живёт столько же, сколько и компоненты, в которых применяется
        val viewModel: HistoryViewModel by currentScope.inject()
        model = viewModel
        model.getResult().observe(this@HistoryActivity, Observer<AppState> {
            renderData(it)
        })
    }

    private fun initViews() {
        history_activity_recyclerview.adapter = adapter
    }
}