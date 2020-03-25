package commanderpepper.catdog.models

sealed class Option {
    object CAT : Option()
    object DOG : Option()
    object BOTH : Option()
    object CATFAV : Option()
    object DOGFAV : Option()
}

fun String.toOption(): Option {
    return when (this) {
        "CAT" -> Option.CAT
        "DOG" -> Option.DOG
        "CATFAV" -> Option.CATFAV
        "DOGFAV" -> Option.DOGFAV
        "BOTH" -> Option.BOTH
        else -> Option.BOTH
    }
}

fun Option.isFav(): Boolean {
    return when (this) {
        is Option.CATFAV -> true
        is Option.DOGFAV -> true
        else -> false
    }
}