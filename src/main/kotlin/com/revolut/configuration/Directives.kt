package com.revolut.configuration

import akka.actor.ActorSystem
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.server.AllDirectives
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import com.revolut.route.CreateAccountRoute

class Directives @Inject constructor(
    private val system: ActorSystem,
    private val http: Http,
    private val materializer: ActorMaterializer,
    private val accountRoute: CreateAccountRoute
) : AllDirectives() {

    fun createRoutes() {

        val routeFlow = accountRoute.route.flow(system, materializer)

        http.bindAndHandle(
            routeFlow,
            ConnectHttp.toHost("localhost", 8080), materializer
        )
    }

}