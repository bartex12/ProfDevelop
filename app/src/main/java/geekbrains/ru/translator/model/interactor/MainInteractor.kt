package geekbrains.ru.translator.model.interactor

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.repository.Repository
import io.reactivex.Single

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
  ) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        //здесь можно описать какую то бизнес-логику, например,
        // что слово должно состоять только из символов и другие правила
        return if (fromRemoteSource) {
          val resultRemote =  remoteRepository.getData(word)
            // возврвщаем   AppState с изменённым значением AppState.Success
            AppState.Success(resultRemote)
        } else {
            val resultLocal =    localRepository.getData(word)
            // возврвщаем   AppState с изменённым значением AppState.Success
            AppState.Success(resultLocal)
        }
    }
}
//в вебинаре сделано так - без доп переменных
//return AppState.Success(
//if (fromRemoteSource) {
//    repositoryRemote
//} else {
//    repositoryLocal
//}.getData(word)
//)