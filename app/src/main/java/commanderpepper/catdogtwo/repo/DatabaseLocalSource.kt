package commanderpepper.catdogtwo.repo

import android.content.Context
import android.util.Log
import commanderpepper.catdogtwo.models.AnimalUrl
import commanderpepper.catdogtwo.room.AnimalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DatabaseLocalSource(val animalDatabase: AnimalDatabase) {

    suspend fun getListFromDatabase(): List<String> = animalDatabase.animalDao().getUrls()

    suspend fun getCatListFromDatabase(): List<String> = animalDatabase.animalDao().getCatUrls()

    suspend fun getDogListFromDatabase(): List<String> = animalDatabase.animalDao().getDogsUrls()

    suspend fun addCattoDatabase(url: String) {
        animalDatabase.animalDao().addUrl(AnimalUrl(url, "CAT"))
    }

    suspend fun addDogtoDatabase(url: String) {
        animalDatabase.animalDao().addUrl(AnimalUrl(url, "DOG"))
    }

    suspend fun deleteCatFromDatabase(url: String) {
        animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "CAT"))
    }

    suspend fun deleteDogFromDatabase(url: String) {
        animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "DOG"))
    }

    suspend fun checkForCatFavorites(): Boolean {
        return animalDatabase.animalDao().checkForCatFavorites() > 0
    }

    suspend fun checkForDogFavorites(): Boolean {
        return animalDatabase.animalDao().checkForDogFavorites() > 0
    }

    /**
     * Get list of favorite cats
     */
    fun getCatUrls(): List<String> {
        var url = listOf<String>()
        runBlocking {
            withContext(Dispatchers.IO) {
                url = animalDatabase.animalDao().getCatUrls()
            }
        }
        return url
    }

    /**
     * Get list of favorite dogs
     */
    fun getDogUrls(): List<String> {
        var url = listOf<String>()
        runBlocking {
            withContext(Dispatchers.IO) {
                url = animalDatabase.animalDao().getDogsUrls()
            }
        }
        return url
    }

    suspend fun getAllFavoriteCatUrls(): List<String> {
        return withContext(Dispatchers.IO) {
            animalDatabase.animalDao().getCatUrls()
        }
    }

    suspend fun getAllFavoriteDogUrls(): List<String> {
        return withContext(Dispatchers.IO) {
            animalDatabase.animalDao().getDogsUrls()
        }
    }

    /**
     * Add a cat to the database
     */
    fun addCat(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                Log.d("SAVECAT", url)
                animalDatabase.animalDao().addUrl(AnimalUrl(url, "CAT"))
                Log.d("SAVECAT", "done")
            }
        }
    }

    /**
     * Add a dog to the database
     */
    fun addDog(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().addUrl(AnimalUrl(url, "DOG"))
            }
        }
    }

    /**
     * Delete a cat from the database
     */
    fun deleteCat(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "CAT"))
            }
        }
    }

    /**
     * Delete a dog from the database
     */
    fun deleteDog(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "DOG"))
            }
        }
    }

    fun checkForAnimalUrl(animalUrl: String): Boolean {
        val result = ConflatedBroadcastChannel<Boolean>()
        result.offer(false)
        runBlocking {
            withContext(Dispatchers.IO) {
                val amount = animalDatabase.animalDao().checkForAnimalUrl(animalUrl)
                if (amount >= 1) {
                    result.offer(true)
                }
            }
        }
        return result.value
    }

    /**
     * Singleton object to access the database
     */
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

        fun getInstance(): DatabaseLocalSource {
            return instance
                ?: throw  UninitializedPropertyAccessException("DatabaseLocalSource must be initialized")
        }

        fun createInstance(context: Context) {
            if (instance == null) {
                synchronized(DatabaseLocalSource::javaClass) {
                    instance = DatabaseLocalSource(AnimalDatabase.getInstance(context))
                }
            }
        }

        fun clearInstance() {
            instance = null
        }

    }
}