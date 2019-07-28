package commanderpepper.catdog

import commanderpepper.catdog.repo.DatabaseLocalSource
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class AnimalLocalDataSourceTest {

    lateinit var localDataSource: DatabaseLocalSource

    @Before
    fun init() {
        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
        localDataSource = DatabaseLocalSource.getInstance(context)!!
    }

    @Test
    fun addAndGetCatUrl() = runBlocking {
        val url = UUID.randomUUID().toString()
        localDataSource.addCat(url)
        val cats = localDataSource.getCatUrls()
        Assert.assertTrue(cats.contains(url))
    }

    @Test
    fun addAndGetDogUrl() = runBlocking {
        val url = UUID.randomUUID().toString()
        localDataSource.addDog(url)
        val dogs = localDataSource.getDogUrls()
        Assert.assertTrue(dogs.contains(url))
    }

    @Test
    fun addAndDeleteCatUrl() = runBlocking {
        val url = UUID.randomUUID().toString()
        localDataSource.addCat(url)
        localDataSource.deleteCat(url)
        val cats = localDataSource.getCatUrls()
        Assert.assertTrue(!cats.contains(url))
    }

    @Test
    fun addAndDeleteDogUrl() = runBlocking {
        val url = UUID.randomUUID().toString()
        localDataSource.addDog(url)
        localDataSource.deleteDog(url)
        val dogs = localDataSource.getDogUrls()
        Assert.assertTrue(!dogs.contains(url))
    }

    @After
    fun cleanUp(){
        localDataSource.animalDatabase.animalDao().clearTableForTesting()
    }
}