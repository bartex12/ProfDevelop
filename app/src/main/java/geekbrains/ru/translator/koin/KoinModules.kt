package geekbrains.ru.translator.koin

import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.viewmodel.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
   single <Repository<List<DataModel>>>(named (NAME_REMOTE)){RepositoryImplementation(RetrofitImplementation()) }
   single <Repository<List<DataModel>>>(named(NAME_LOCAL)) {RepositoryImplementation(RoomDataBaseImplementation()) }
}

val mainScreen = module {
    factory { MainInteractor(get(named (NAME_REMOTE)), get(named( NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}