package com.example.yugioh.room.type_converter

import androidx.room.TypeConverter
import com.example.yugioh.data.remote.responses.monster_card.Data_Monster_Card
import com.example.yugioh.room.data.ItemDB
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


object myTypeConverter {
    @TypeConverter
    fun FromItemToString(itemDB: ItemDB?):String?{
        if(itemDB==null)return null
        val gson = Gson()
        val type: Type = object : TypeToken<ItemDB?>() {}.type
        return gson.toJson(itemDB,type)
    }

    @TypeConverter
    fun FromStringToItem(itemDB: String?): ItemDB?{
        if(itemDB==null)return null
        val gson = Gson()
        val type: Type = object : TypeToken<ItemDB?>() {}.type
        return gson.fromJson(itemDB,type)
    }

    @TypeConverter
    fun FromListToString(lista:List<String>?):String?{
      if(lista ==null ) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(lista)
    }
    @TypeConverter
    fun FromDataCardToString(lista:String?):List<String>?{
        if(lista==null)return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return  gson.fromJson(lista,type)

    }
    @TypeConverter
    fun FromDataCardToString(dataCard:Data_Monster_Card?):String?{
        if(dataCard==null)return null
        val gson = Gson()
        val type: Type = object : TypeToken<Data_Monster_Card?>() {}.type
        return  gson.toJson(dataCard,type)
    }
    @TypeConverter
    fun FromStringToDataCard(dataCard:String?):Data_Monster_Card?{
        if(dataCard == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<Data_Monster_Card?>() {}.type
        return  gson.fromJson(dataCard,type)

    }


}