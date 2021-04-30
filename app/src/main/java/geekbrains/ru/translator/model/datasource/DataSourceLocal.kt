package geekbrains.ru.translator.model.datasource

import geekbrains.ru.model.data.AppState

interface DataSourceLocal<T>:DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}