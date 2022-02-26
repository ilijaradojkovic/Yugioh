package com.example.yugioh.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yugioh.room.data.ItemDB
import com.example.yugioh.room.type_converter.myTypeConverter


@Database(entities = [ItemDB::class],version = 1)
@TypeConverters(myTypeConverter::class)
abstract class Database:RoomDatabase(){

  abstract  fun getDao():Dao

}