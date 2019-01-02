package com.revolut.di

import akka.actor.ActorSystem
import akka.http.javadsl.Http
import akka.stream.ActorMaterializer
import com.google.inject.AbstractModule

class MainModule : AbstractModule() {

    override fun configure() {
        val system = ActorSystem.create("routes")

        bind(ActorSystem::class.java).toInstance(system)
        bind(Http::class.java).toInstance(Http.get(system))
        bind(ActorMaterializer::class.java).toInstance(ActorMaterializer.create(system))
    }
}