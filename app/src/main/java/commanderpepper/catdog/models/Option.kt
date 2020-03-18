package commanderpepper.catdog.models

import android.graphics.Path

sealed class Option{
    object CAT : Option()
    object DOG : Option()
    object BOTH : Option()
    object CATFAV: Option()
    object DOGFAV: Option()
}