package com.bartex.core2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekbrains.ru.translator.model.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T:AppState> (
    protected val _liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData()
): ViewModel(){
     //изменяем открытый метод на абстрактный
    abstract fun getData(word: String, isOnline: Boolean)
    //обрабатываем ошибки в конкретной имплементации базовой ВьюМодели
    abstract fun handleError(error: Throwable)

//     Объявляем свой собственный скоуп
//     В качестве аргумента передается CoroutineContext, который мы составляем
//     через "+" из трех частей:
//     - Dispatchers.Main говорит, что результат работы предназначен для
//     основного потока;
//     - SupervisorJob() позволяет всем дочерним корутинам выполняться
//     независимо, то есть, если какая-то корутина упадёт с ошибкой, остальные
//     будут выполнены нормально;
//     - CoroutineExceptionHandler позволяет перехватывать и отрабатывать
//     ошибки и краши

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    // Завершаем все незавершённые корутины, потому что пользователь закрыл экран
    //отдельный метод - так как используется в нескольких местах
    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}