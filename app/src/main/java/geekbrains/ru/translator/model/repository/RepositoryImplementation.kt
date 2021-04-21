package geekbrains.ru.translator.model.repository

import android.util.Log
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource
import io.reactivex.Single

class RepositoryImplementation /*@Inject constructor*/(val dataSource:DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    companion object{
        const val TAG = "33333"
    }

    private val cash = mutableMapOf<String, List<DataModel>>()

    override fun getData(word: String): Single<List<DataModel>> {
        // TODO потом надо переделать на базу данных
        if (cash.contains(word)){
            Log.d(TAG, "RepositoryImplementation onCreate cash содержит $word - берём из кэша" )
            return Single.just(cash[word])
        }else{
            Log.d(TAG, "RepositoryImplementation onCreate cash не содержит $word - идём в сеть")
            return  dataSource.getData(word).doAfterSuccess {cash[word] = it}
            }
        }
    }

