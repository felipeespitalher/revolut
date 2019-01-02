package com.revolut.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


data class Item @JsonCreator constructor(
    @JsonProperty("name") val name: String,
    @JsonProperty("id") val id: Long
)