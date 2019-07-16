package commanderpepper.catdog

import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class CatDogRepositoryTest {
    private lateinit var catDogRepository: CatDogRepository

    @Before
    fun init() {
        catDogRepository = CatDogRepository
    }

    @Test
    fun getCatUrl() {
        var catUrl: String
        GlobalScope.launch {
            catUrl = catDogRepository.getCatUrl()
            assertThat(catUrl, CoreMatchers.containsString("/"))
        }
    }

    @Test
    fun getDogUrl() {
        var dogUrl: String
        GlobalScope.launch {
            dogUrl = catDogRepository.getDogUrl()
            assertThat(dogUrl, CoreMatchers.containsString("random"))
        }
    }

    @Test
    fun getCatWithDeferred() {
        val catUrl = runBlocking {
            withContext(Dispatchers.Default) {
                catDogRepository.getCatUrl()
            }
        }
        assertThat(catUrl, CoreMatchers.containsString("/"))
    }

    @Test
    fun getDogWithDeferred() {
        val dogUrl = runBlocking {
            withContext(Dispatchers.Default) {
                catDogRepository.getDogUrl()
            }
        }
        assertThat(dogUrl, CoreMatchers.containsString("random"))
    }

    @Test
    fun testSingleton() {
        val uuid = catDogRepository.hashCode()
        catDogRepository = CatDogRepository

        val newUUID = catDogRepository.hashCode()
        assert(uuid == newUUID)
    }


}