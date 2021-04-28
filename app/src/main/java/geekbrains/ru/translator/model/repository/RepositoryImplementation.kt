package geekbrains.ru.translator.model.repository

import android.util.Log
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource

class RepositoryImplementation (private val dataSource:DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    companion object{
        const val TAG = "33333"
    }

    private val cash = mutableMapOf<String, List<DataModel>>()

    override suspend fun getData(word: String): List<DataModel> {
        // TODO потом надо переделать на базу данных
        return if (cash.contains(word)){
            Log.d(TAG, "RepositoryImplementation getData cash содержит $word - берём из кэша" )
            cash[word]!!  //возвращаемое значение
        }else{
            Log.d(TAG, "RepositoryImplementation getData cash не содержит $word - идём в сеть")
            val listDataModel =   dataSource.getData(word)
            cash[word] =  dataSource.getData(word)
            listDataModel //возвращаемое значение
        }
        }
    }

