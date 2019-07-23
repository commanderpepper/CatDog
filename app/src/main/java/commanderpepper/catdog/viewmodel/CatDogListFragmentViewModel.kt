package commanderpepper.catdog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import commanderpepper.catdog.repo.CatDogRepository

class CatDogListFragmentViewModel : ViewModel() {
    val catDogUrls = MutableLiveData<List<String>>()
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