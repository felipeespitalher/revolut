package com.revolut.route

import akka.http.javadsl.server.Route

abstract class BaseRoute {

    abstract fun createRoute(): Route

}