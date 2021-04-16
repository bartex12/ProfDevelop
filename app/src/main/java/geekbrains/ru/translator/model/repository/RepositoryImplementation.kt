package geekbrains.ru.translator.model.repository

import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    private val cash = mutableMapOf<String, List<DataModel>>()

    override fun getData(word: String): Observable<List<DataModel>> {
        if (cash.contains(word)){
            return Observable.just(cash[word])
        }else{
            return dataSource.getData(word).doOnNext {
                cash[word] = it
            }
        }
    }
}
