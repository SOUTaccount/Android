package com.stebakov.checkerror

class TestCloudDataSource: CloudDataSource {
    private var count = 0
    override fun getJoke(callback: JokeCloudCallback) {
        val joke = JokeServerModel("TestType$count",count)
        callback.provide(joke)
        count++
    }
}