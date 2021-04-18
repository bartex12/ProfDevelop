package geekbrains.ru.translator.dagger

import geekbrains.ru.translator.model.interactor.Interactor
import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.Repository
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    //@Singleton
    fun  provideInteractor(
        @Named(NAME_REMOTE) remoteRepository: Repository<List<DataModel>>,
        @Named(NAME_LOCAL)localRepository: Repository<List<DataModel>>): Interactor<AppState> =
        MainInteractor(remoteRepository, localRepository)

}