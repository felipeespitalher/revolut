package com.revolut.route

import akka.Done
import akka.http.javadsl.marshallers.jackson.Jackson
import akka.http.javadsl.model.StatusCodes
import akka.http.javadsl.server.Directives.*
import akka.http.javadsl.server.PathMatchers
import akka.http.javadsl.server.Route
import com.google.inject.Inject
import com.revolut.data.Account
import com.revolut.data.Item
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class CreateAccountRoute @Inject constructor() : BaseRoute() {

    override fun createRoute(): Route {
        return concat(
            get {
                pathPrefix("account") {
                    path(PathMatchers.longSegment()) { id: Long ->
                        val futureMaybeItem = fetchItem(id)

                        onSuccess(futureMaybeItem) { optionalItem ->
                            optionalItem.map { it ->
                                completeOK(it, Jackson.marshaller())
                            }.orElseGet {
                                complete(StatusCodes.NOT_FOUND, "Not Found")
                            }
                        }
                    }
                }
            },
            post {
                path("create") {
                    entity(Jackson.unmarshaller(Account::class.java)) { order ->
                        val futureSaved = saveOrder(order)
                        onSuccess(
                            futureSaved
                        ) { complete("order created") }
                    }
                }
            }
        )
    }


    // (fake) async database query api
    private fun fetchItem(itemId: Long): CompletionStage<Optional<Item>> {
        return CompletableFuture.completedFuture(Optional.of(Item("foo", itemId)))
    }

    // (fake) async database query api
    private fun saveOrder(account: Account): CompletionStage<Done> {
        return CompletableFuture.completedFuture(Done.getInstance())
    }

}