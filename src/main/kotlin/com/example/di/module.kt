package com.example.di

import com.example.data.repository.DataRepoImplementation
import com.example.domain.repository.dataRepoInterface
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    val connectionString = "mongodb+srv://Horlarwarler:PDiP6ahQFykSFahs@cluster0.za1kgzq.mongodb.net/?retryWrites=true&w=majority"
    single {
            KMongo.createClient(connectionString)
                .coroutine
                .getDatabase("RecruitmentDatabase")
        }
    single<dataRepoInterface> {
            DataRepoImplementation(get())
    }



}