package commanderpepper.catdog.models

enum class PhotoType(val types: List<String>) {
    CAT(listOf("jpg", "png")),
    DOG(listOf("jpg", "png", "gif"))
}