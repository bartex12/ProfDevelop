package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Observable
import io.reactivex.Single

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Single<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
