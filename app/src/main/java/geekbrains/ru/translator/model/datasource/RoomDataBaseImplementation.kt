package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Single

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> {
       return Single.just(listOf())
    }
}
