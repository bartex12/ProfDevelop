package geekbrains.ru.translator.koin

import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

// Для удобства создадим две переменные: в одной находятся зависимости,
// используемые во всём приложении, во второй - зависимости конкретного экрана

// Функция single сообщает Koin, что эта зависимость должна храниться
// в виде синглтона (в Dagger есть похожая аннотация)
// Аннотация named выполняет аналогичную Dagger функцию
val application = module {
   single <Repository<List<DataModel>>>(named (NAME_REMOTE)){RepositoryImplementation(RetrofitImplementation()) }
   single <Repository<List<DataModel>>>(named(NAME_LOCAL)) {RepositoryImplementation(RoomDataBaseImplementation()) }
}

// Функция factory сообщает Koin, что эту зависимость нужно создавать каждый
// раз заново, что как раз подходит для Activity и её компонентов.
val mainScreen = module {
    factory { MainInteractor(get(named (NAME_REMOTE)), get(named( NAME_LOCAL))) }
    viewModel {
        MainViewModel(get())
    }
    //factory { MainViewModel(get()) } //так тоже работает
}