package commanderpepper.catdog.repo

import android.content.Context
import commanderpepper.catdog.models.AnimalUrl
import commanderpepper.catdog.room.AnimalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseLocalSource(val animalDatabase: AnimalDatabase) {

    suspend fun getList(): List<String> {
        var url = listOf<String>()
        withContext(Dispatchers.IO) {
            url = animalDatabase.animalDao().getUrls()
        }
        return url
    }

    suspend fun getCatUrls(): List<String> {
        var url = listOf<String>()
        withContext(Dispatchers.IO) {
            url = animalDatabase.animalDao().getCatUrls()
        }
        return url
    }

    suspend fun getDogUrls(): List<String> {
        var url = listOf<String>()
        withContext(Dispatchers.IO) {
            url = animalDatabase.animalDao().getDogsUrls()
        }
        return url
    }

    suspend fun addCat(url: String) {
        withContext(Dispatchers.IO) {
            animalDatabase.animalDao().addUrl(AnimalUrl(url, "CAT"))
        }
    }

    suspend fun addDog(url: String) {
        withContext(Dispatchers.IO) {
            animalDatabase.animalDao().addUrl(AnimalUrl(url, "DOG"))
        }
    }

    suspend fun deleteCat(url: String) {
        withContext(Dispatchers.IO) {
            animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "CAT"))
        }
    }

    suspend fun deleteDog(url: String) {
        withContext(Dispatchers.IO) {
            animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "DOG"))
        }
    }

    companion object {
        private var instance: DatabaseLocalSource? = null

        fun getInstance(context: Context): DatabaseLocalSource? {
            if (instance == null) {
                synchronized(DatabaseLocalSource::javaClass) {
                    instance = DatabaseLocalSource(AnimalDatabase.getInstance(context))
                }
            }
            return instance
        }

        fun clearInstance() {
            instance = null
        }

    }
}