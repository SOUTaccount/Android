package com.stebakov.getjoke

import retrofit2.Call
import retrofit2.http.GET

interface JokeService {

    @GET("https://geek-jokes.sameerkumar.website/api?format=json")
    fun getJoke(): Call<JokeDTO>
}

interface ServiceCallback{

    fun returnSuccess(data: JokeDTO)

    fun returnError(type: ErrorType)
}

enum class ErrorType{
    NO_CONNECTION,
    OTHER
}