package geekbrains.ru.translator.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //Инициализируем Koin в приложении:
        startKoin {
            androidContext(applicationContext)
            //было так до создания динамичекой фичи для historyScreen
            //modules(listOf(application, mainScreen, historyScreen))
        }
    }
}