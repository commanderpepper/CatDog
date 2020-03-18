package commanderpepper.catdog.repo

import android.content.Context
import android.util.Log
import commanderpepper.catdog.models.AnimalUrl
import commanderpepper.catdog.room.AnimalDatabase
import kotlinx.coroutines.Dispatchers
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