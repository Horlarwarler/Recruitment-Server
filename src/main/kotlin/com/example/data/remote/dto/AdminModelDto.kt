package com.example.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class AdminModelDto(
    val username:String,
    val password:String
)
