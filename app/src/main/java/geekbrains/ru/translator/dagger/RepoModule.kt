package geekbrains.ru.translator.dagger


import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    fun provideDataSourceRemote(
        @Named(NAME_REMOTE) dataSourceRemote:DataSource<List<DataModel>>)
            : Repository<List<DataModel>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    fun provideDataSourceLocal(
        @Named(NAME_LOCAL) dataSourceLocal:DataSource<List<DataModel>>)
            : Repository<List<DataModel>> = RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBaseImplementation()
 //создание интерфейса  - 1.20 вебинара
}

