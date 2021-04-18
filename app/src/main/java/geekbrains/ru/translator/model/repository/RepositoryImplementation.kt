package geekbrains.ru.translator.model.repository

import android.util.Log
import geekbrains.ru.translator.dagger.NAME_LOCAL
import geekbrains.ru.translator.dagger.NAME_LOCAL_2
import geekbrains.ru.translator.dagger.NAME_REMOTE
import geekbrains.ru.translator.dagger.NAME_REMOTE_2
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class RepositoryImplementation @Inject constructor(val dataSource:DataSource<List<DataModel>>) :
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

