package commanderpepper.catdog

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import commanderpepper.catdog.room.AnimalDatabase
import org.junit.Before
import org.junit.Test

class AnimalDatabaseTest {
    lateinit var database: AnimalDatabase

    @Before
    fun init() {
        val context = InstrumentationRegistry.getContext()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AnimalDatabase::class.java
        ).build()
    }

    @Test
    fun addUrl(){
        val url = "this url"
        val animal = "DOG"

        database.animalDao().
    }
}