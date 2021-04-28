package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //метод теперь возвращает Deferred
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<DataModel>>
}
