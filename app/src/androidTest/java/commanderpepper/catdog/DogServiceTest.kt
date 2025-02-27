package commanderpepper.catdog

import commanderpepper.catdogtwo.retrofit.DogService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.junit.Before
import org.junit.Test

class DogServiceTest {
    private lateinit var dogService: DogService

    @Before
    fun init() {
        dogService = DogService.create()
    }

    @Test
    fun getDog() = runBlocking {
        val dog = dogService.getDog()
        assertThat(dog, `is`(IsNull.notNullValue()))
    }


    @Test
    fun checkDog() = runBlocking {
        val dog = dogService.getDog()
        assertThat(dog.url, CoreMatchers.containsString("random"))
    }

}
