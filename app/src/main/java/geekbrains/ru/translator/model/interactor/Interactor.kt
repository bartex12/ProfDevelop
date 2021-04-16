package com.anikin.aleksandr.simplevocabulary.viewmodel

import io.reactivex.Observable
import io.reactivex.Single

interface Interactor<T> {

    fun getData(word: String, fromRemoteSource: Boolean): Single<T>
}