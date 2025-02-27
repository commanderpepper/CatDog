package commanderpepper.catdog

import commanderpepper.catdogtwo.retrofit.CatService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.junit.Before
import org.junit.Test

class CatServiceTest {
    private lateinit var catService: CatService

    @Before
    fun init() {
        catService = CatService.create()
    }

    @Test
    fun getCat() = runBlocking {
        val cat = catService.getCat()
        assertThat(cat, `is`(IsNull.notNullValue()))
    }

    @Test
    fun checkCat() = runBlocking {
        val cat = catService.getCat()
        assertThat(cat.file, containsString("/"))
    }

}