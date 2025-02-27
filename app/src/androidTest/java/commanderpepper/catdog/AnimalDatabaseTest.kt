package commanderpepper.catdog

import androidx.room.Room
import commanderpepper.catdogtwo.models.AnimalUrl
import commanderpepper.catdogtwo.room.AnimalDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class AnimalDatabaseTest {
    private lateinit var database: AnimalDatabase

    @Before
    fun init() {
        val context =
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(
            context,
            AnimalDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun addUrl() = runBlocking {
        val url = "this url"
        val animal = "DOG"

        launch {
            val dog = AnimalUrl(url, animal)
            database.animalDao().addUrl(dog)
        }

        val rUrl = database.animalDao().getDogsUrls()
        assertThat(rUrl.first(), CoreMatchers.equalTo(url))
    }

    @Test
    fun addAndGetUrls() = runBlocking {
        val cat = AnimalUrl("test", "CAT")
        val ca2 = AnimalUrl("test1", "CAT")

        database.animalDao().addUrl(cat)
        database.animalDao().addUrl(ca2)

        val animals = database.animalDao().getCatUrls()
        assertThat(animals.size, CoreMatchers.equalTo(2))
    }

    @Test
    fun addThenDeleteUrl() = runBlocking {
        val cat = AnimalUrl("test", "CAT")
        database.animalDao().addUrl(cat)
        database.animalDao().deleteRowUsingUrl(cat.url)

        val size = database.animalDao().getUrls().size
        assertThat(size, CoreMatchers.equalTo(0))
    }

}