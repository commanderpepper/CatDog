package commanderpepper.catdog

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import commanderpepper.catdog.models.AnimalUrl
import commanderpepper.catdog.room.AnimalDatabase

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertThat

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
    fun addUrl() {
        val url: String = "this url"
        val animal = "DOG"

        val dog = AnimalUrl(url, animal)

        database.animalDao().addUrl(dog)
        val rUrl = database.animalDao().getDogsUrls()
        assertThat(rUrl.first(), CoreMatchers.equalTo(url))
    }

    @Test
    fun addAndGetUrls() {
        val cat = AnimalUrl("test", "CAT")
        val ca2 = AnimalUrl("test1", "CAT")

        database.animalDao().addUrl(cat)
        database.animalDao().addUrl(ca2)

        val animals = database.animalDao().getCatUrls()
        assertThat(animals.size, CoreMatchers.equalTo(2))
    }

    @Test
    fun addThenDeleteUrl() {
        val cat = AnimalUrl("test", "CAT")
        database.animalDao().addUrl(cat)
        database.animalDao().deleteRowUsingUrl(cat.url)

        val size = database.animalDao().getUrls().size
        assertThat(size, CoreMatchers.equalTo(0))

    }
    
}