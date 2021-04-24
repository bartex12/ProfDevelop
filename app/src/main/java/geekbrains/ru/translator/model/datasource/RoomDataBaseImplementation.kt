package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel

//потом видимо результат из базы данных будет возвращаться в виде Deferred и нужно будет добавить await()
class RoomDataBaseImplementation : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
       return listOf()
    }
}
