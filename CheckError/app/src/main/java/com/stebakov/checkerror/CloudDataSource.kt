package com.stebakov.checkerror

interface CloudDataSource {

    fun getJoke(callback: JokeCloudCallback)
}