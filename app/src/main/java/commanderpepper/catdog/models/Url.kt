package commanderpepper.catdog.models

sealed class UrlAnimal{
    data class UrlDog(val dog: Dog): UrlAnimal()
    data class UrlCat(val cat: Cat): UrlAnimal()
}