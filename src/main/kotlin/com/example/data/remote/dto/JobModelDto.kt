package com.example.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class JobModelDto(
    val jobTitle:String,
    val jobType:String = "Full-Time",
    val jobLocation:String = "Ibadan"
)
