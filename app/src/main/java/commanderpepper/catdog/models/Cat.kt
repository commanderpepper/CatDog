package commanderpepper.catdog.models

import com.squareup.moshi.Json

data class Cat(
    @Json(name = "file")
    val file: String
)


