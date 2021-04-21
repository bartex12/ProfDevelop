package geekbrains.ru.translator.model.interactor

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.repository.Repository
import io.reactivex.Single

class MainInteractor/*@Inject constructor*/(
    /*@Named(NAME_REMOTE) */val remoteRepository: Repository<List<DataModel>>,
    /*@Named(NAME_REMOTE) */val localRepository: Repository<List<DataModel>>
  ) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Single<AppState> {
        //здесь можно описать какую то бизнес-логику, например,
        // что слово должно состоять только из символов и другие правила
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
