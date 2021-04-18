package geekbrains.ru.translator.view.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import geekbrains.ru.translator.model.data.AppState

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model:ViewModel

    abstract  fun renderData(appState: AppState)

}
