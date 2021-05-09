package com.bartex.core2

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}