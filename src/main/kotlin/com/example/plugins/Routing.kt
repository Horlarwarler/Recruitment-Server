package com.example.plugins

import com.example.data.database.AdminDatabaseModel
import com.example.data.mapper.convertToDto
import com.example.data.mapper.convertToModel
import com.example.data.mapper.convertToStatusDto
import com.example.data.remote.dto.AdminModelDto
import com.example.data.remote.dto.ApplicantModelDto
import com.example.domain.repository.dataRepoInterface
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

fun Application.configureRouting(

) {
    val dataRepoInterface by inject<dataRepoInterface> ()
    val baseUrl = System.getenv("RAILWAY_URL")

    routing {

        post("/apply"){
            val dataReceive = call.receiveMultipart()
            var resumeFile: PartData.FileItem? = null
            var coverLetterFile: PartData.FileItem? = null
            var formInput: ApplicantModelDto = ApplicantModelDto()

            dataReceive.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        if (part.name == "resume") {
                            resumeFile = part
                        } else if (part.name == "coverLetter") {
                            coverLetterFile = part
                        }
                    }
                    is PartData.FormItem -> {
                        when(part.name){
                            "firstName" -> {
                                formInput = formInput.copy(firstName = part.value)
                            }
                            "lastName" -> {
                                formInput = formInput.copy(lastName = part.value)
                            }
                            "middleName" -> {
                                formInput = formInput.copy(middleName = part.value)
                            }
                            "email" -> {
                                formInput = formInput.copy(email = part.value)
                            }
                            "phoneNumber" -> {
                                formInput = formInput.copy(phoneNumber = part.value)
                            }
                            "address" -> {
                                formInput = formInput.copy(address = part.value)
                            }
                            "city" -> {
                                formInput = formInput.copy(city = part.value)
                            }
                            "state" -> {
                                formInput = formInput.copy(state = part.value)
                            }
                            "zipCode" -> {
                                formInput = formInput.copy(zipCode = part.value)
                            }
                            "degree" -> {
                                formInput = formInput.copy(degree = part.value)
                            }
                            "education" -> {
                                formInput = formInput.copy(education = part.value)
                            }
                            "month" -> {
                                formInput = formInput.copy(month = part.value)
                            }
                            "day" -> {
                                formInput = formInput.copy(day = part.value)
                            }
                            "year" -> {
                                formInput = formInput.copy(year = part.value)
                            }


                        }
                    }
                    else -> {
                        part.dispose()
                    }
                }
            }
            val applicationId = dataRepoInterface.addNewApplicant(formInput.convertToModel())
            resumeFile?.let {
                val fileName=  generateFileName(it.originalFileName!!, applicationId)
                val file = File("resume/$fileName")
                it.streamProvider().use { input ->
                    file.outputStream().buffered().use { output ->
                        input.copyTo(output)
                    }
                }

            }
            coverLetterFile?.let {
                val  fileName=  generateFileName(it.originalFileName!!, applicationId)
                val  file = File("coverLetter/$fileName")
                it.streamProvider().use {
                    input ->
                    file.outputStream().buffered().use { output ->
                        input.copyTo(output)
                    }
                }
            }

            val applicationDetails = dataRepoInterface.getApplicationStatus(applicationId)!!

            val updatedDetails = applicationDetails.copy(
                resume = resumeFile?.originalFileName!!,
                coverLetter = coverLetterFile?.originalFileName!!
            )
            dataRepoInterface.updateApplicantDetails(updatedDetails)
            call.respond(HttpStatusCode.OK, applicationId)

        }

        post("register") {
            val details = call.receive<AdminModelDto>()
            val admin = AdminDatabaseModel(
                username = details.username,
                password = details.password
            )
            dataRepoInterface.registerAdmin(admin)
            call.respond(HttpStatusCode.OK)
        }
        get("joblisting") {
            val jobListing = dataRepoInterface.getAllJobEntry()
            val convertToDto = jobListing.map {
                it.convertToDto()
            }
            call.respond(HttpStatusCode.OK, convertToDto)
        }

        get("trackapplication"){
            val application = call.request.queryParameters["applicationid"]!!
            val applicationDetails = dataRepoInterface.getApplicationStatus(application)
            if(applicationDetails == null){
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(HttpStatusCode.OK, applicationDetails.convertToStatusDto())
        }
        post("login"){
            val loginDetails = call.receive<AdminModelDto>()
            val username  = loginDetails.username
            val password  = loginDetails.password

            val admin:AdminDatabaseModel? = dataRepoInterface.getAdmin(username)
            if(admin == null){
                call.respond(HttpStatusCode.NotFound)
                return@post
            }
            if (username != admin.username || password != admin.password){
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }
            call.respond(HttpStatusCode.OK)
        }

        get("applicants"){
            val applicants = dataRepoInterface.getAllApplicant().map {
                it.convertToDto()
            }
            call.respond(HttpStatusCode.OK, applicants)
        }
        get("/approveapplicant"){
            val applicationId = call.request.queryParameters["applicationid"]!!
            dataRepoInterface.acceptApplicant(applicationId)
            call.respond(HttpStatusCode.OK)
        }
        get("/rejectapplicant"){
            val applicationId = call.request.queryParameters["applicationid"]!!
            dataRepoInterface.rejectApplicant(applicationId)
            call.respond(HttpStatusCode.OK)
        }

    }
}

private fun generateFileName(originalFilename: String, applicationId: String): String {
    val extension = originalFilename.substringAfterLast(".")
    val randomId = UUID.randomUUID().toString().substring(0, 8)
    val timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
    return "$applicationId-$randomId-$timeStamp.$extension"
}
