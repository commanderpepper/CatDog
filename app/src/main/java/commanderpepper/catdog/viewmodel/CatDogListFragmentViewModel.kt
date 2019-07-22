package commanderpepper.catdog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import commanderpepper.catdog.repo.CatDogRepository

class CatDogListFragmentViewModel : ViewModel() {
    val catDogUrls = MutableLiveData<List<String>>()

    init {
        catDogUrls.value = CatDogRepository.getListOfCatUrls(10)
    }

    fun addCatUrl(amount: Int) {
        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(amount)
    }
}