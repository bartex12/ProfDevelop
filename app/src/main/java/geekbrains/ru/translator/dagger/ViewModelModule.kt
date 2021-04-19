package geekbrains.ru.translator.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import geekbrains.ru.translator.viewmodel.MainViewModel

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    //объясняем даггеру, как создавать Map - создаём свою аннотацию
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    //здесь дальше могут быть другие ViewModel
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel::class)
//    protected abstract fun mainViewModel2(mainViewModel: MainViewModel): ViewModel
}
