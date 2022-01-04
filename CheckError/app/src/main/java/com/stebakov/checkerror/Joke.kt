package com.stebakov.checkerror

import androidx.annotation.DrawableRes

class BaseJoke(text: String, punchline: String) : Joke(text, punchline) {
    override fun getIconResId() = R.drawable.outline_favorite_border_24
}

class FavoriteJoke(text: String, punchline: String) : Joke(text, punchline) {
    override fun getIconResId() = R.drawable.outline_favorite_24
}

class FailedJoke(text: String) : Joke(text, "") {
    override fun getIconResId() = 0
}

abstract class Joke(private val text: String, private val punchline: String) {

    protected fun getJokeUi() = "$text\n$punchline"

    @DrawableRes
    protected abstract fun getIconResId(): Int

    fun map(callback: DataCallback) = callback.run {
        provideText(getJokeUi())
        provideIconRes(getIconResId())
    }
}