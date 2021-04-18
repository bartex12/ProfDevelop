package geekbrains.ru.translator.dagger


import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.App
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App {
        return app
    }
//
//    @Provides
//    fun mainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()
}