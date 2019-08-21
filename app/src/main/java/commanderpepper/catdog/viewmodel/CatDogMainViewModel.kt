package commanderpepper.catdog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
//        viewModelScope.launch {
//            catUrl = CatDogRepository.getCat()
//        }
        catUrl = CatDogRepository.getUseableCatUrlFromAPI()
        return catUrl
    }

    fun getUseableDogUrl(): String {
        if (dogUrl != "") {
            return dogUrl
        }

        viewModelScope.launch {
            val z = async(Dispatchers.IO) {
                CatDogRepository.getDog()
            }

            withContext(Dispatchers.Main) {
                dogUrl = z.await()
            }
        }

//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                dogUrl = CatDogRepository.getDog()
//            }
//        }
//        dogUrl = CatDogRepository.getUseableDogUrlFromAPI()
        return dogUrl
    }
}