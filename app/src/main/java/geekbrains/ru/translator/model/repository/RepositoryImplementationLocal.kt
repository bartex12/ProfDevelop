package geekbrains.ru.translator.model.repository

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSourceLocal


// RepositoryImplementationLocal теперь содержит два метода, наследуется от
// RepositoryLocal и в конструктор получает инстанс DataSourceLocal
class RepositoryImplementationLocal(val dataSource: DataSourceLocal<List<DataModel>>) :RepositoryLocal<List<DataModel>>{

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        return dataSource.saveToDB(appState)
    }
}