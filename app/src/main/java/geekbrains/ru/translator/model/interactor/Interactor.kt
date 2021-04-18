package geekbrains.ru.translator.model.interactor

import io.reactivex.Single

interface Interactor<T> {

    fun getData(word: String, fromRemoteSource: Boolean): Single<T>
}