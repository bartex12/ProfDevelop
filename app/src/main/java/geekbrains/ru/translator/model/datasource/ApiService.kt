package geekbrains.ru.translator.model.datasource

import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Single<List<DataModel>>
}
