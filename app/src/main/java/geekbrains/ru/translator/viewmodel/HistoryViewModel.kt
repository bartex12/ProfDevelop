package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import com.bartex.core2.BaseViewModel
import geekbrains.ru.model.data.AppState
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.translator.model.interactor.HistoryInteractor
import kotlinx.coroutines.launch


class HistoryViewModel(private val interactor: HistoryInteractor) :
    BaseViewModel<AppState>() {

    //не понял, зачем копируем переменную из базового класса
    private val liveDataForViewToObserve: LiveData<AppState> = _liveDataForViewToObserve

    fun  getResult(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _liveDataForViewToObserve.postValue(parseLocalResults(interactor.getData(word, isOnline)))
    }

    private fun parseLocalResults(appState: AppState): AppState? {
        val results = arrayListOf<DataModel>()
        when (appState) {
            is AppState.Success -> {
                val dataModels: List<DataModel> = appState.data as List<DataModel>
                if (dataModels.isNotEmpty()) {
                    for (searchResult in dataModels) {
                        results.add(DataModel(searchResult.text, arrayListOf()))
                    }
                }
            }
        }
        return AppState.Success(results)
    }

    override fun handleError(error: Throwable) {
        _liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _liveDataForViewToObserve.value = AppState.Success(null)
        // Set View to original state in  onStop
        super.onCleared()
    }

}