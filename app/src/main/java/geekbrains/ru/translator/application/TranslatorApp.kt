package geekbrains.ru.translator.application

import android.app.Application
import geekbrains.ru.translator.koin.application
import geekbrains.ru.translator.koin.mainScreen
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(application, mainScreen))
        }
    }

}