package commanderpepper.catdog

import commanderpepper.catdogtwo.repo.CatDogRepository
import org.junit.Before

class CatDogRepositoryTest {
    private lateinit var catDogRepository: CatDogRepository

    @Before
    fun init() {
        catDogRepository = CatDogRepository
    }

//    @Test
//    fun getCatUrl() {
//        var catUrl: String
//        GlobalScope.launch {
//            catUrl = catDogRepository.getCatUrl()
//            assertThat(catUrl, CoreMatchers.containsString("/"))
//        }
//    }
//
//    @Test
//    fun getDogUrl() {
//        var dogUrl: String
//        GlobalScope.launch {
//            dogUrl = catDogRepository.getDogUrl()
//            assertThat(dogUrl, CoreMatchers.containsString("random"))
//        }
//    }
//
//    @Test
//    fun getCatWithDeferred() {
//        val catUrl = runBlocking {
//            withContext(Dispatchers.Default) {
//                catDogRepository.getCatUrl()
//            }
//        }
//        assertThat(catUrl, CoreMatchers.containsString("/"))
//    }
//
//    @Test
//    fun getDogWithDeferred() {
//        val dogUrl = runBlocking {
//            withContext(Dispatchers.Default) {
//                catDogRepository.getDogUrl()
//            }
//        }
//        assertThat(dogUrl, CoreMatchers.containsString("random"))
//    }
//
//    @Test
//    fun testSingleton() {
//        val uuid = catDogRepository.hashCode()
//        catDogRepository = CatDogRepository
//
//        val newUUID = catDogRepository.hashCode()
//        assert(uuid == newUUID)
//    }


}