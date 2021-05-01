package geekbrains.ru.translator.koin

import androidx.room.Room
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.view.main.MainInteractor
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.model.repository.RepositoryImplementationLocal
import geekbrains.ru.translator.model.repository.RepositoryLocal
import geekbrains.ru.translator.model.room.HistoryDataBase
import geekbrains.ru.translator.view.main.MainViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

// Объявим функцию, которая будет создавать зависимости по требованию
fun injectDependencies() = loadModules
// Ленивая инициализация создаст зависимости только тогда, когда функция будет
// вызвана
private val loadModules by lazy {
    // Функция библиотеки Koin
    loadKoinModules(listOf(application, mainScreen))
}

// Остальное никак не изменилось

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
}
