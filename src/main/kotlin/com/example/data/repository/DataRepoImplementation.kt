package com.example.data.repository

import com.example.data.database.AdminDatabaseModel
import com.example.data.database.ApplicantDatabaseModel
import com.example.data.database.JobDatabaseModel
import com.example.domain.repository.dataRepoInterface
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase

import org.litote.kmongo.eq

class DataRepoImplementation(
    private val database: CoroutineDatabase
) : dataRepoInterface{

    private val jobDatabaseCollection = database.getCollection<JobDatabaseModel>()
    private  val applicantDatabaseCollection = database.getCollection<ApplicantDatabaseModel>()
    private val adminsDataCollection = database.getCollection<AdminDatabaseModel>()
    override suspend fun getAllJobEntry(): List<JobDatabaseModel> {
       // code to get all job  database
        val jobs = jobDatabaseCollection.find().toList()
        return  jobs
    }

    override suspend fun registerAdmin(adminDatabaseModel: AdminDatabaseModel) {
        adminsDataCollection.insertOne(adminDatabaseModel)
    }

    override suspend fun getAllApplicant(): List<ApplicantDatabaseModel> {
        return applicantDatabaseCollection.find().toList()
    }

    override suspend fun getAdmin(username: String): AdminDatabaseModel? {
      return  adminsDataCollection.findOne(AdminDatabaseModel::username eq username)
    }

    override suspend fun addJobEntry(job: JobDatabaseModel) {
        // code to add job to database
        jobDatabaseCollection.insertOne(job)
    }

    override suspend fun removeJob(jobId: String) {
        // code to remove job from database
        val objectId = ObjectId(jobId)
        jobDatabaseCollection.deleteOneById(objectId)
    }

    override suspend fun addNewApplicant(applicants: ApplicantDatabaseModel): String {
        //code to add new applicants to database
        val result = applicantDatabaseCollection.insertOne(applicants)

        if (result.wasAcknowledged()) {
            val randomId = applicants.randomId
            return getRandom(randomId)._id.toString()
        }

        return "63eb3ae9905b8f6e8f3deb79"


    }

    override suspend fun getApplicantDetails(applicationId: String): ApplicantDatabaseModel {
        val objectId = ObjectId(applicationId)
        return applicantDatabaseCollection.findOneById(objectId)!!
    }

    override suspend fun getApplicationStatus(applicationId: String): ApplicantDatabaseModel? {
        return try {
            val objectId = ObjectId(applicationId)
            applicantDatabaseCollection.findOneById(objectId)
        } catch (e:Exception){
            null
        }
    }

    override suspend fun acceptApplicant(applicationId: String) {
        var applicationDetails = getApplicantDetails(applicationId)
        val objectId = ObjectId(applicationId)

        applicationDetails = applicationDetails.copy(status = "Accepted")
        applicantDatabaseCollection.updateOneById(objectId , update = applicationDetails )
    }

    override suspend fun updateApplicantDetails(applicant: ApplicantDatabaseModel) {
        val applicationId = applicant._id!!
        applicantDatabaseCollection.updateOneById(applicationId , update = applicant )
    }

    override suspend fun rejectApplicant(applicationId: String) {
        var applicationDetails = getApplicantDetails(applicationId)
        val objectId = ObjectId(applicationId)

        applicationDetails = applicationDetails.copy(status = "Rejected")
        applicantDatabaseCollection.updateOneById(objectId , update = applicationDetails )
    }

    override suspend fun searchJobEntry(searchQuery: String): List<JobDatabaseModel> {
        return jobDatabaseCollection.find(JobDatabaseModel::jobTitle eq searchQuery).toList()
    }
    private suspend fun getRandom(id:String): ApplicantDatabaseModel{
        return applicantDatabaseCollection.find(ApplicantDatabaseModel::randomId eq id).toList().firstOrNull()!!
    }
}
