package com.stebakov.checkerror

import retrofit2.Call
import retrofit2.http.GET

interface JokeService {

    @GET("https://geek-jokes.sameerkumar.website/api?format=json")
    fun getJoke(): Call<JokeServerModel>
}

interface ServiceCallback{

    fun returnSuccess(data: JokeServerModel)

    fun returnError(type: ErrorType)
}
