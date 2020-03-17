package commanderpepper.catdog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commanderpepper.catdog.models.Cat
import commanderpepper.catdog.models.Dog
import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Used with the main activity
 */
class CatDogMainViewModel : ViewModel() {
    private var dogUrl: String = ""
    private var catUrl: String = ""

    suspend fun getUseableCatUrl(): String {

        if (catUrl != "") {
            return catUrl
        }

        CatDogRepository.getCatFlow().catch {
            catUrl = "error"
        }.collect {
            catUrl = it.file
        }

        return catUrl
    }

    suspend fun getUseableDogUrl(): String {
        if (dogUrl != "") {
            return dogUrl
        }

        CatDogRepository.getDogFlow().catch {
            dogUrl = "error"
        }.collect {
            dogUrl = it.url
        }

        return dogUrl
    }
}