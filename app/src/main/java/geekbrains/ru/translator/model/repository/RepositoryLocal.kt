package geekbrains.ru.translator.model.repository

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel

interface RepositoryLocal<T> : Repository<T>{
    suspend fun saveToDB(appState: AppState)
}