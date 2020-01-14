package commanderpepper.catdog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.*

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

        val catDeffered = viewModelScope.async(Dispatchers.IO) {
            CatDogRepository.getCat()
        }

        runBlocking {
            catUrl = try {
                catDeffered.await()
            } catch (exception: Exception) {
                Log.e("Main", exception.toString())
                "error"
            }
        }

        return catUrl
    }

    fun getUseableDogUrl(): String {
        if (dogUrl != "") {
            return dogUrl
        }

        val dogDeffered = viewModelScope.async(Dispatchers.IO) {
            CatDogRepository.getDog()
        }

        runBlocking {
            dogUrl = try {
                dogDeffered.await()
            } catch (exception: Exception) {
                Log.e("Main", exception.toString())
                "error"
            }
        }

        return dogUrl
    }
}