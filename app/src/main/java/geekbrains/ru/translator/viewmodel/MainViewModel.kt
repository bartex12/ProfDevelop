package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.data.Meanings
import geekbrains.ru.translator.model.interactor.MainInteractor
import kotlinx.coroutines.launch


class MainViewModel (private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    //не понял, зачем копируем переменную из базового класса
    private val liveDataForViewToObserve:LiveData<AppState> = _liveDataForViewToObserve

    fun getResult():LiveData<AppState>{
        return liveDataForViewToObserve
    }


    override fun getData(word: String, isOnline: Boolean) {
        //показываем крутилку прогресса
        _liveDataForViewToObserve.value = AppState.Loading(null)
        //останавливаем запущенные корутины, так как они уже не нужны при запросе новых данных
        cancelJob()
        // Запускаем корутину для асинхронного доступа к серверу с помощью  launch
        viewModelCoroutineScope.launch {startInteractor(word, isOnline)}
    }
    // Добавляем suspend
    // withContext(Dispatchers.IO) указывает, что доступ в сеть должен
    // осуществляться через диспетчер IO (который предназначен именно для таких
    // операций), хотя это и не обязательно указывать явно, потому что Retrofit
    // и так делает это благодаря CoroutineCallAdapterFactory(). Это же касается и Room
    private suspend fun startInteractor(word: String, online: Boolean) {
        _liveDataForViewToObserve.postValue(parseResults(interactor.getData(word, online)))
//        withContext(Dispatchers.IO){
//            //если бы не парсить , то было бы  .postValue(interactor.getData(word, online))
//            _liveDataForViewToObserve.postValue(parseResults(interactor.getData(word, online)))
//        }
    }

    private fun  parseResults(appState:AppState):AppState{
       val results = arrayListOf<DataModel>()
       when(appState){
           is AppState.Success -> {
             val   searchResults = appState.data
               if (searchResults!=null){
                   //для каждой DataModel из списка всех DataModel смотрим что там в Meanings
                   for(searchResult in searchResults){
                       parseResult(searchResult, results)
                   }
               }
           }
       }
       return AppState.Success(results)
   }

  fun  parseResult(dataModel:DataModel, newDataModel:ArrayList<DataModel>) {
      if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
          val newMeanings = arrayListOf<Meanings>()
          for (meaning in dataModel.meanings) {
              if (meaning.translation != null && !meaning.translation.text.isNullOrBlank()) {
                  newMeanings.add(Meanings(translation = meaning.translation, imageUrl = meaning.imageUrl,
                  transcription = meaning.transcription, soundUrl = meaning.soundUrl))
              }
          }
          if (newMeanings.isNotEmpty()){
              newDataModel.add(DataModel(dataModel.text, newMeanings))
          }
      }
  }

    //вручную кладем ошибку из AppState
    override fun handleError(error: Throwable) {
        _liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    //неочевидная вещь
    override fun onCleared() {
        //до super.onCleared() в котором  cancelJob() - завершаются все корутины
        _liveDataForViewToObserve.value = AppState.Success(null)
        super.onCleared()
    }
}