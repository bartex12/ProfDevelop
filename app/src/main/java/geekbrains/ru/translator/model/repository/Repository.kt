package geekbrains.ru.translator.model.repository

import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Single

interface Repository<T> {

    fun getData(word: String): Single<List<DataModel>>
}
