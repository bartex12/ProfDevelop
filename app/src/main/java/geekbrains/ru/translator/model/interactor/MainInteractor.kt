package geekbrains.ru.translator.model.interactor

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.dagger.NAME_LOCAL
import geekbrains.ru.translator.dagger.NAME_REMOTE
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.repository.Repository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class MainInteractor@Inject constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<DataModel>>,
    @Named(NAME_REMOTE) val localRepository: Repository<List<DataModel>>
  ) : Interactor<AppState> {
/*    @Inject
    @Named(NAME_REMOTE) lateinit var remoteRepository: Repository<List<DataModel>>

    @Inject
    @Named(NAME_LOCAL) lateinit var localRepository: Repository<List<DataModel>>*/

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
