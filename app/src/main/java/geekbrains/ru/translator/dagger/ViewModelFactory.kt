package geekbrains.ru.translator.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.viewmodel.MainViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject
constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = viewModels[modelClass]
            ?: viewModels.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
