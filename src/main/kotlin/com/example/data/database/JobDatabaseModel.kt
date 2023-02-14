package com.example.data.database

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class JobDatabaseModel(
    @BsonId
    val _id: Id<JobDatabaseModel>? = null,
    val jobTitle:String,
    val jobType:String = "Full-Time",
    val jobLocation:String = "Ibadan"

    )
