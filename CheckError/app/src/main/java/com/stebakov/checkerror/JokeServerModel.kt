package com.stebakov.checkerror

import com.google.gson.annotations.SerializedName

class JokeServerModel(
    @SerializedName("joke")
    private val joke: String,
    @SerializedName("id")
    private val id: Int
) {
    fun toJoke() = BaseJoke(joke, "END JOKE")
    fun toFavoriteJoke() = FavoriteJoke(joke,"END FAVORITE JOKE")
    fun toJokeRealm(): JokeRealm{
        return JokeRealm().also {
            it.id = id
            it.text = joke
        }
    }
    fun change(cacheDataSource: CacheDataSource) = cacheDataSource.addOrRemove(id,this)
}