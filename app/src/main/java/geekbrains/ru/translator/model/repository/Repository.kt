package geekbrains.ru.translator.model.repository

import io.reactivex.Observable

interface Repository<AppState> {

    fun getData(word: String): Observable<AppState>
}
