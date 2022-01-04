package com.stebakov.checkerror

import io.realm.Realm

class BaseCacheDataSource(private val realm: Realm): CacheDataSource {
    override fun addOrRemove(id: Int, joke: JokeServerModel): Joke {
        realm.let {
            val jokeRealm = it.where(JokeRealm::class.java).equalTo("id",id).findFirst()
            return if (jokeRealm == null){
                val newJoke = joke.toJokeRealm()
                it.executeTransactionAsync{ transaction ->
                    transaction.insert(newJoke)
                }
                joke.toFavoriteJoke()
            } else{
                it.executeTransaction{
                    jokeRealm.deleteFromRealm()
                }
                joke.toJoke()
            }
        }
    }

    override fun getJoke(jokeCachedCallback: JokeCachedCallback) {
        realm.use {
            val jokes = it.where(JokeRealm::class.java).findAll()
            if (jokes.isEmpty())
                jokeCachedCallback.fail()
            else
                jokes.random().let { joke->
                    jokeCachedCallback.provide(
                        JokeServerModel(
                            joke.text,
                            joke.id
                        )
                    )
                }
        }
    }

}