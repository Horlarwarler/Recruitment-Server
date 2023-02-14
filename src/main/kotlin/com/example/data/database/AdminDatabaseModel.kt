package com.example.data.database

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class AdminDatabaseModel(
    @BsonId
    val _id: Id<AdminDatabaseModel>? = null,
    val username:String,
    val password:String
)
