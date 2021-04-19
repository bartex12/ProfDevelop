package geekbrains.ru.translator.dagger

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import geekbrains.ru.translator.application.TranslatorApp
import geekbrains.ru.translator.view.main.MainActivity
import geekbrains.ru.translator.viewmodel.MainViewModel
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
    //пишем все классы, куда будем инжектить
    fun inject(activity: MainActivity)
}