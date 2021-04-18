package geekbrains.ru.translator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


//всё в параметрах - нахрена это усложнение с базовыми классами для учебного проекта - непонятно.
//  GeekBrains заколебал уже своими абстракциями
//очередной вынос мозга безо всяких видимых причин, да ещё Даггер этот идиотский
abstract class BaseViewModel<T:AppState> (

): ViewModel(){

    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData()
    protected val compositeDisposable:CompositeDisposable = CompositeDisposable()
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()

    open fun getData(word: String, isOnline: Boolean):LiveData<T>{
        return liveDataForViewToObserve
    }

    //хоть одна полезная вещь -compositeDisposable - для удаления всех подписок сразу
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}