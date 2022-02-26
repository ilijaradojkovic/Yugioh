package com.example.yugioh

sealed class Response<out T>(val data:T?=null){
    
  class  Loading<T>:Response<T>()
    data class  Error<T>(val message1: String?): Response<T>()
    data class  Success<T>( val dataSucceded:T) : Response<T>(dataSucceded)

  override fun equals(other: Any?): Boolean {
    return false
  }
}
