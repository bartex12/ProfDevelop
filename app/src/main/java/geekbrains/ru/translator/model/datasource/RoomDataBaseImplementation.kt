package geekbrains.ru.translator.model.datasource

import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.translator.model.room.History
import geekbrains.ru.translator.model.room.HistoryDao

//потом видимо результат из базы данных будет возвращаться в виде Deferred и нужно будет добавить await()
class RoomDataBaseImplementation(val historyDao: HistoryDao) : DataSourceLocal<List<DataModel>> {

    // Возвращаем список всех слов в виде понятного для Activity List<DataModel>
    override suspend fun getData(word: String): List<DataModel> {
        //делаем не как в методичке, а через map - в стиле Котлин
       return historyDao.getAll().map{
          DataModel(text = it.word, meanings = null)
       }
    }

    // Метод сохранения слова в БД. Он будет использоваться в интеракторе
    override suspend fun saveToDB(appState: AppState) {
        //если всё не null тогда вставляем в базу
        convertDataModelToHistory(appState)?. let{
            historyDao.insert(it)
        }
    }

    private fun convertDataModelToHistory(appState: AppState): History? {
        return when(appState){
            is AppState.Success -> {
                if (appState.data.isNullOrEmpty() || appState.data!![0].text.isNullOrEmpty()){
                    null // если что-то null = возвращаем null
                }else{
                    History(appState.data!![0].text!!, null) //иначе пишем в базу
                }
            }
            else -> null
        }
    }
}
