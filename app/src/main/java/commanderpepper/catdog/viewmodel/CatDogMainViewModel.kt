package commanderpepper.catdog.viewmodel

import androidx.lifecycle.ViewModel
import commanderpepper.catdog.repo.CatDogRepository

/**
 * Used with the main activity
 */
class CatDogMainViewModel : ViewModel() {
    var dogUrl: String = ""
    var catUrl: String = ""

    fun getUseableCatUrl(): String {
        if (catUrl != "") {
            return catUrl
        }
        catUrl = CatDogRepository.getUseableCatUrlFromAPI()
        return catUrl
    }

    fun getUseableDogUrl(): String {
        if (dogUrl != "") {
            return dogUrl
        }
        dogUrl = CatDogRepository.getUseableDogUrlFromAPI()
        return dogUrl
    }
}