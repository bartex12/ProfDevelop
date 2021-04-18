package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.RepositoryImplementation

class MainViewModel(
    val interactor:Interactor<AppState> = MainInteractor(
        RepositoryImplementation(RetrofitImplementation()),
        RepositoryImplementation(RoomDataBaseImplementation())
    )
):BaseViewModel<AppState>() {

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                //в момент подписки запускаем Loading - в MVVM кладём Loading в value
                .doOnSubscribe {
                    liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribe ({
                    liveDataForViewToObserve.value =it
                },{error->
                    liveDataForViewToObserve.value = AppState.Error(error)
                })
            //subscribeWith мне привычнее заменить на subscribe, хотя с subscribeWith код чище
            //.subscribeWith(getObserver())
        )
        return liveDataForViewToObserve  //это то же что и return super.getData(word, isOnline)
    }
}