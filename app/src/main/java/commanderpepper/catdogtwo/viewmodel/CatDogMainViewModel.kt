package commanderpepper.catdogtwo.viewmodel

import androidx.lifecycle.ViewModel
import commanderpepper.catdogtwo.repo.CatDogRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect


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
            catUrl = "error"
        }.collect {
            dogUrl = it.url
        }

        return dogUrl
    }
}