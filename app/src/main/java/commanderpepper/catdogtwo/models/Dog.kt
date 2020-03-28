package commanderpepper.catdogtwo.models

import com.squareup.moshi.Json

data class Dog(
    @Json(name = "url")
    val url: String
)