package commanderpepper.catdog.models

inline class Url(val value: String)

sealed class UrlAnimal{
    data class UrlDog(val dog: Dog): UrlAnimal()
    data class UrlCat(val cat: Cat): UrlAnimal()
}