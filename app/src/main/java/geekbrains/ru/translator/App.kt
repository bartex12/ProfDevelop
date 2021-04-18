package geekbrains.ru.translator

import android.app.Application
import geekbrains.ru.translator.dagger.AppComponent
import geekbrains.ru.translator.dagger.AppModule
import geekbrains.ru.translator.dagger.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this //здесь определяем свойство instance - контекст приложения

        appComponent = DaggerAppComponent.builder()
            //.appModule(AppModule(this))
            .build()
    }
}