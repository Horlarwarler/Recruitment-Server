package com.example.data.mapper

import com.example.data.database.AdminDatabaseModel
import com.example.data.database.ApplicantDatabaseModel
import com.example.data.database.JobDatabaseModel
import com.example.data.remote.dto.AdminModelDto
import com.example.data.remote.dto.ApplicantModelDto
import com.example.data.remote.dto.ApplicationStatusDto
import com.example.data.remote.dto.JobModelDto
import java.util.Calendar


fun AdminDatabaseModel.convertToDto():AdminModelDto{
    //convert to adminModelDto
    return  AdminModelDto(
        username, password
    )
}

fun AdminModelDto.convertToDatabase():AdminDatabaseModel{
    return AdminDatabaseModel(
        username = username,
        password = password
    )
}

fun ApplicantDatabaseModel.convertToDto(): ApplicantModelDto{
    return  ApplicantModelDto(
        id = _id.toString(),
        address = address,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        state = state,
        zipCode = zipCode,
        degree = degree,
        education = education,
        status = status,
        coverLetter = coverLetter,
        resume = resume

    )
}

fun ApplicantModelDto.convertToModel(): ApplicantDatabaseModel {
    val dateApplied = Calendar.getInstance().time.toString()
    return  ApplicantDatabaseModel(
        address = address,
        firstName = firstName,
        lastName = lastName,
        middleName = middleName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        state = state,
        zipCode = zipCode,
        degree = degree,
        education = education,
        coverLetter = coverLetter,
        status = status,
        resume = resume,
        date = dateApplied,
        month = month,
        day = day,
        year = year

    )
}

fun ApplicantDatabaseModel.convertToStatusDto():ApplicationStatusDto{
    val name = "${this.firstName} ${this.middleName?:""} ${this.lastName}"
    return  ApplicationStatusDto(
        name = name,
        dateApplied = date,
        status =status
    )
}

fun JobModelDto.convertToDatabase(): JobDatabaseModel {
    return JobDatabaseModel(
        jobTitle = jobTitle,
        jobLocation = jobLocation,
        jobType = jobType
    )
}

fun JobDatabaseModel.convertToDto(): JobModelDto {
    return JobModelDto(
        jobTitle = jobTitle,
        jobLocation = jobLocation,
        jobType = jobType
    )
}