package com.revolut.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Account @JsonCreator constructor(
    @JsonProperty val fullName: String,
    @JsonProperty val documentNumber: String
)