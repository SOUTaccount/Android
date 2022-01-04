package com.stebakov.checkerror

interface Model {

    fun getJoke()

    fun init(callback: JokeCallback)

    fun clear()

    fun changeJokeStatus(jokeCallback: JokeCallback)

    fun chooseDataSource(cached: Boolean)
}

interface ResultCallback {

    fun provideJoke(joke: Joke)
}