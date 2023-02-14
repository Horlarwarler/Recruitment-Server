package com.example.data.database

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.Date
import java.util.UUID

data class ApplicantDatabaseModel(
    @BsonId
    val _id: Id<ApplicantDatabaseModel>? = null,
    val firstName: String,
    val lastName: String,
    val middleName:String? = null,
    val email: String,
    val phoneNumber: String,
    val address:
    String,
    val city: String,
    val state: String,
    val zipCode: String,
    val degree:String,
    val education: String,
    val resume: String,
    val coverLetter: String,
    val status: String = "Pending",
    val date:String,
    val month:String,
    val day:String  ,
    val  year:String,
    val randomId:String = UUID.randomUUID().toString().substring(0,7)

    )
