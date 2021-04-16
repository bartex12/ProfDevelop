package geekbrains.ru.translator.model.datasource

import io.reactivex.Observable
import io.reactivex.Single

interface DataSource<T> {

    fun getData(word: String):Single<T>
}
