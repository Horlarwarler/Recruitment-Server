package com.example.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationStatusDto(
    val name:String,
    val dateApplied:String,
    val status:String
)
