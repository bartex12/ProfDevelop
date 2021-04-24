package geekbrains.ru.translator.application

import android.app.Application
import androidx.room.Room
import geekbrains.ru.translator.koin.application
import geekbrains.ru.translator.koin.historyScreen
import geekbrains.ru.translator.koin.mainScreen
import geekbrains.ru.translator.model.room.HistoryDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //можно создать базу здесь, но у нас это делает Coin в модуле
       // database =  Room.databaseBuilder(applicationContext, HistoryDataBase::class.java, HistoryDataBase.DB_NAME).build()
        //Инициализируем Koin в приложении:
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }

//    companion object{
//        lateinit var database:HistoryDataBase
//    }

}