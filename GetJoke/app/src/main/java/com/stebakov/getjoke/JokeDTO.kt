package com.stebakov.getjoke

import com.google.gson.annotations.SerializedName

class JokeDTO(
    @SerializedName("joke")
    private val joke: String
) {
    fun toJoke() = Joke(joke, "END JOKE")
}