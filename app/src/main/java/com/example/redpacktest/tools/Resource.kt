package com.example.redpacktest.tools

import java.lang.Exception

sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data:T):Resource<T>()
    data class Failure<out T>(val exception: Exception):Resource<T>()

}

sealed class ResourceString<out String> {
    class Loading<out String>: ResourceString<String>()
    data class Success<out String>(val data:String):ResourceString<String>()
    data class Failure<out String>(val exception: Exception):ResourceString<String>()

}