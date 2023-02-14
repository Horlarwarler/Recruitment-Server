package com.example.data.remote.dto

import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class ApplicantModelDto(
    val id: String? = null,
    val firstName: String ="",
    val lastName: String= "",
    val middleName:String? = null,
    val email: String= "",
    val phoneNumber: String= "",
    val address: String= "",
    val city: String= "",
    val state: String= "",
    val zipCode: String= "",
    val degree:String= "",
    var education: String= "",
    val resume: String= "",
    val coverLetter: String= "",
    val status: String = "Pending",
    val dateApplied:String? = null,
    val month:String= "",
    val day:String  = "",
    val  year:String = ""

)
