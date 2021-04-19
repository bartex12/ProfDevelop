package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.data.Meanings
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.interactor.Interactor
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import javax.inject.Inject


class MainViewModel @Inject constructor(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    fun getResult():LiveData<AppState>{
        return liveDataForViewToObserve
    }


    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                //в момент подписки запускаем Loading - в MVVM кладём Loading в value
                .doOnSubscribe {
                    liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribe ({
                    liveDataForViewToObserve.value = parseResults(it)
                },{error->
                    liveDataForViewToObserve.value = AppState.Error(error)
                })
            //subscribeWith мне привычнее заменить на subscribe, хотя с subscribeWith код чище
            //.subscribeWith(getObserver())
        )
        return liveDataForViewToObserve  //это то же что и return super.getData(word, isOnline)
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

}