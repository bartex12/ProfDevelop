package geekbrains.ru.translator.model.interactor

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryLocal

class HistoryInteractor(
    val repositoryRemote : Repository<List<DataModel>>,
    val repositoryRemoteLocal : RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    //тонкий момент - возврат <List<DataModel> внутри AppState.Success
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
     return AppState.Success(
         if (fromRemoteSource){
         repositoryRemote
     }else{
         repositoryRemoteLocal
     }.getData(word))
    }
}