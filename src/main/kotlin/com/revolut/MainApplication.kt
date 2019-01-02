package com.revolut

import com.google.inject.Guice
import com.revolut.configuration.Directives
import com.revolut.di.MainModule

class MainApplication {
    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val injector = Guice.createInjector(MainModule())
            val directives = injector.getInstance(Directives::class.java)
            directives.createRoutes()
        }
    }
}