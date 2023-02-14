package com.example.di

import com.example.data.repository.DataRepoImplementation
import com.example.domain.repository.dataRepoInterface
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
            KMongo.createClient("mongodb://localhost:27017")
                .coroutine
                .getDatabase("RecruitmentDatabase")
        }
    single<dataRepoInterface> {
            DataRepoImplementation(get())
    }



}