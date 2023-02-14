package com.example

import com.example.di.mainModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import org.koin.ktor.plugin.Koin

fun main() {
    val baseUrl = System.getenv("RAILWAY_URL")

    embeddedServer(Netty,port=3000, host = baseUrl, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    //instantiate a mongodb database
    install(Koin){
        modules(mainModule)
    }
    configureSerialization()
    configureRouting()
}
