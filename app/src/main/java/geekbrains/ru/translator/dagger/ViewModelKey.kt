package geekbrains.ru.translator.dagger

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass
/*
ViewModelKeys helps you map your ViewModel classes
so ViewModelFactory can correctly provide/inject them.
 */

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)