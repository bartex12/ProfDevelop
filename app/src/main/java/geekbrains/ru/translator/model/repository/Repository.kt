package geekbrains.ru.translator.model.repository

import geekbrains.ru.model.data.DataModel

interface Repository<T> {
    suspend fun getData(word: String): List<DataModel>
}
