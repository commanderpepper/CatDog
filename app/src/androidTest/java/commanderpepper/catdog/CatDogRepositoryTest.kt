package commanderpepper.catdog

import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.Before
import org.junit.Test

class CatDogRepositoryTest {
    private lateinit var catDogRepository: CatDogRepository

    @Before
    fun init() {
        catDogRepository = CatDogRepository.create()
    }

    @Test
    fun getCatUrl() {
        var catUrl = ""
        GlobalScope.launch {
            catUrl = catDogRepository.getCatUrl()
        }
        MatcherAssert.assertThat(catUrl, CoreMatchers.containsString("random"))
    }
}