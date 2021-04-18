package geekbrains.ru.translator.dagger

import dagger.Component
import geekbrains.ru.translator.viewmodel.MainViewModel
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        InteractorModule::class,
        RepoModule::class,
    ]
)

interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}