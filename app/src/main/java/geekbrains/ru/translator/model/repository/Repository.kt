package geekbrains.ru.translator.model.repository

import io.reactivex.Observable
import io.reactivex.Single

interface Repository<AppState> {

    fun getData(word: String): Single<AppState>
}
