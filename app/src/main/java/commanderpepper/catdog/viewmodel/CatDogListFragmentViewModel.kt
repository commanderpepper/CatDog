package commanderpepper.catdog.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import commanderpepper.catdog.repo.CatDogRepository
import commanderpepper.catdog.repo.DatabaseLocalSource

class CatDogListFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val catDogUrls = MutableLiveData<List<String>>()
    var localDataSource : DatabaseLocalSource = DatabaseLocalSource.getInstance(getApplication())!!
    lateinit var option: String

    fun getUrls() {
        when (option) {
            "CAT" -> catDogUrls.value = CatDogRepository.getListOfCatUrls(10)
            "DOG" -> catDogUrls.value = CatDogRepository.getListOfDogUrls(10)
            "BOTH" -> {
                catDogUrls.value = CatDogRepository.getListOfCatUrls(1)
                for (i in 1 until 10) {
                    if (i % 2 == 0) {
                        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(1)
                    } else {
                        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfDogUrls(1)
                    }
                }
            }
            "CATFAV" -> catDogUrls.value = localDataSource.getCatUrls()
            "DOGFAV" -> catDogUrls.value = localDataSource.getDogUrls()
            "BOTHFAV" -> catDogUrls.value = localDataSource.getCatUrls()
        }
    }

    fun addUrls(amount: Int) {
        when (option) {
            "CAT" -> addCatUrlsToList(amount)
            "DOG" -> addDogUrlsToList(amount)
            "BOTH" -> addBothUrlstoList(amount)
        }
    }

    fun addCatUrlsToList(amount: Int) {
        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(amount)
    }

    fun addDogUrlsToList(amount: Int) {
        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfDogUrls(amount)
    }

    fun addBothUrlstoList(amount: Int) {
        for (i in 0 until amount) {
            if (i % 2 == 0) {
                catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(1)
            } else {
                catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfDogUrls(1)
            }
        }
    }
}