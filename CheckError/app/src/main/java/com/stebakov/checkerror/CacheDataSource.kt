package com.stebakov.checkerror

interface CacheDataSource {

    fun addOrRemove(id: Int, joke: JokeServerModel) : Joke

    fun getJoke(jokeCachedCallback: JokeCachedCallback)
}