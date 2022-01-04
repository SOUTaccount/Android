package com.stebakov.checkerror

class ViewModel(private val model: Model) {

    private var dataCallback: DataCallback? = null
    private val jokeCallback = object : JokeCallback{
        override fun provide(joke: Joke) {
            dataCallback?.let {
                joke.map(it)
            }
        }
    }

    fun init(callback: DataCallback){
        dataCallback = callback
        model.init(jokeCallback)
    }

    fun changeJokeStatus(){
        model.changeJokeStatus(jokeCallback)
    }

    fun getJoke(){
        model.getJoke()
    }

    fun clear(){
        dataCallback = null
        model.clear()
    }

    fun chooseFavorites(favorites: Boolean){
        model.chooseDataSource(favorites)
    }
}

interface TextCallback{

    fun provideText(text: String)
}