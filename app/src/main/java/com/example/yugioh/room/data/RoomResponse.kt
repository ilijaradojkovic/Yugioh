package com.example.yugioh.room.data

sealed class RoomResponse<T>(val message:String="",val data:T?=null){

    class  Loading<T>():RoomResponse<T>()
    class Error<T>(s:String):RoomResponse<T>(message = s)
    class Data(itemDB: List<ItemDB>):RoomResponse< List<ItemDB>>(data=itemDB)
}
