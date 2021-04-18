package geekbrains.ru.translator.dagger

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.Repository
import javax.inject.Named
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    fun  provideInteractor(
        @Named(NAME_REMOTE) remoteRepository: Repository<List<DataModel>>,
        @Named(NAME_LOCAL)localRepository: Repository<List<DataModel>>): Interactor<AppState> =
        MainInteractor(remoteRepository, localRepository)

}