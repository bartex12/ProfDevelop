package geekbrains.ru.translator.application

import android.app.Application
import geekbrains.ru.translator.dagger.AppComponent
import geekbrains.ru.translator.dagger.DaggerAppComponent

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val component =
            DaggerAppComponent.builder().appContext(this) .build()
        TranslatorApp.component = component
    }

    companion object{
        lateinit var component:AppComponent
    }
}