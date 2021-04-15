package geekbrains.ru.translator.presenter

import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import geekbrains.ru.translator.model.interactor.MainInteractor
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.rx.ISchedulerProvider
import geekbrains.ru.translator.rx.SchedulerProvider
import geekbrains.ru.translator.view.base.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T : AppState, V : View>(
    // Обратите внимание, что Интерактор мы создаём сразу в конструкторе
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: ISchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view  //currentView содержит ссылку на MainActivity, которая реализует View
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                 //в момент подписки запускаем Loading - в MVVM это делается во ViewModel
                // когда кладём Loading в value
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribe ({
                    currentView?.renderData(it)
                },{
                    currentView?.renderData(AppState.Error(it))
                })
            //subscribeWith мне привычнее заменить на subscribe
            //.subscribeWith(getObserver())
        )
    }

//    private fun getObserver(): DisposableObserver<AppState> {
//        return object : DisposableObserver<AppState>() {
//
//            override fun onNext(appState: AppState) {
//                currentView?.renderData(appState)
//            }
//
//            override fun onError(e: Throwable) {
//                currentView?.renderData(AppState.Error(e))
//            }
//
//            override fun onComplete() {
//            }
//        }
//    }
}
