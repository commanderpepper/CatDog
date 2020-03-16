package commanderpepper.catdog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commanderpepper.catdog.models.Cat
import commanderpepper.catdog.models.Dog
import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Used with the main activity
 */
class CatDogMainViewModel : ViewModel() {
    var dogUrl: String = ""
        private set
    var catUrl: String = ""
        private set

    val catFlow: Flow<Cat> by lazy {
        CatDogRepository.getCatFlow()
    }
    val dogFlow by lazy {
        CatDogRepository.getDogFlow()
    }

    suspend fun getCatFlow(): Flow<Cat> {
        return if (catUrl == "") {
            CatDogRepository.getCatFlow().collect {
                catUrl = it.file
            }
            flow {
                emit(Cat(catUrl))
            }
        } else {
            flow {
                emit(Cat(catUrl))
            }
        }
    }

    suspend fun getDogFlow(): Flow<Dog> {
        return if (dogUrl == "") {
            CatDogRepository.getCatFlow().collect {
                dogUrl = it.file
            }
            flow {
                emit(Dog(dogUrl))
            }
        } else {
            flow {
                emit(Dog(dogUrl))
            }
        }
    }

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