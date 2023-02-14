package com.example.domain.repository

import com.example.data.database.AdminDatabaseModel
import com.example.data.database.ApplicantDatabaseModel
import com.example.data.database.JobDatabaseModel

interface dataRepoInterface {
    suspend fun getAllJobEntry():List<JobDatabaseModel>
    suspend fun addJobEntry(job:JobDatabaseModel)
    suspend fun removeJob(jobId:String)

    suspend fun  addNewApplicant(applicants: ApplicantDatabaseModel):String

    suspend fun  getApplicantDetails(applicationId:String): ApplicantDatabaseModel

    suspend fun  getApplicationStatus(applicationId: String): ApplicantDatabaseModel?

    suspend fun  acceptApplicant(applicationId: String)

    suspend fun  rejectApplicant(applicationId: String)

    suspend fun  searchJobEntry(searchQuery:String): List<JobDatabaseModel>

    suspend fun  getAllApplicant():List<ApplicantDatabaseModel>

    suspend fun getAdmin(username:String):AdminDatabaseModel?

    suspend fun  updateApplicantDetails(applicant: ApplicantDatabaseModel)

    suspend fun  registerAdmin(adminDatabaseModel: AdminDatabaseModel)



}