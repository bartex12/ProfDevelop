package geekbrains.ru.translator.koin

import androidx.room.Room
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.interactor.HistoryInteractor
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.model.repository.RepositoryImplementationLocal
import geekbrains.ru.translator.model.repository.RepositoryLocal
import geekbrains.ru.translator.model.room.HistoryDataBase
import geekbrains.ru.translator.viewmodel.HistoryViewModel
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
    // single указывает, что БД должна быть в единственном экземпляре
    single{ Room.databaseBuilder(get(), HistoryDataBase::class.java, HistoryDataBase.DB_NAME).build()}
    //плучаем Dao
    single{get<HistoryDataBase>().historyDao()}

   single <Repository<List<DataModel>>>{RepositoryImplementation(RetrofitImplementation()) }
   single <RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get())) }
}

// Функция factory сообщает Koin, что эту зависимость нужно создавать каждый
// раз заново, что как раз подходит для Activity и её компонентов.
val mainScreen = module {
    factory { MainViewModel(get()) } //так тоже работает
    factory { MainInteractor(get(), get()) }
    //viewModel { MainViewModel(get())}
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}