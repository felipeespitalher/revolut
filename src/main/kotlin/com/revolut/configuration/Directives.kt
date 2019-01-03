package com.revolut.configuration

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.model.HttpRequest
import akka.http.javadsl.model.HttpResponse
import akka.http.javadsl.server.AllDirectives
import akka.stream.ActorMaterializer
import akka.stream.javadsl.Flow
import com.google.inject.Inject
import com.revolut.route.BaseRoute
import org.reflections.Reflections


class Directives @Inject constructor(
    private val system: ActorSystem,
    private val http: Http,
    private val materializer: ActorMaterializer
) : AllDirectives() {

    fun createRoutes() {
        val reflections = Reflections("com.revolut")
        val subTypes = reflections.getSubTypesOf(BaseRoute::class.java)
        for (route in subTypes) {
            bind(route.newInstance().createRoute().flow(system, materializer))
        }
    }

    private fun bind(routeFlow: Flow<HttpRequest, HttpResponse, NotUsed>?) {
        http.bindAndHandle(
            routeFlow,
            ConnectHttp.toHost("localhost", 8080), materializer
        )
    }

}