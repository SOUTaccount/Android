package com.stebakov.checkerror

interface JokeCachedCallback {

    fun provide(jokeServerModel: JokeServerModel)
    fun fail()
}