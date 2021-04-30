package geekbrains.ru.translator.model.interactor

import com.bartex.core2.Interactor
import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryLocal


class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: RepositoryLocal<List<DataModel>>
  ) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        //здесь можно описать какую то бизнес-логику, например,
        // что слово должно состоять только из символов и другие правила
       val appState:AppState
       if (fromRemoteSource) {
          appState = AppState.Success( remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState =   AppState.Success(localRepository.getData(word))
        }
        // возвращаем   AppState
        return  appState
    }
}
