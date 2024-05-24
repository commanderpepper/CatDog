package commanderpepper.catdog

import commanderpepper.catdogtwo.retrofit.NewCatService
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull

class NewCatServiceTest {

    private lateinit var newCatService: NewCatService

    @Before
    fun init() {
        newCatService = NewCatService.create()
    }

    @Test
    fun getCatImage(){
        val image = newCatService.getCatImage()
        MatcherAssert.assertThat(image, CoreMatchers.`is`(IsNull.notNullValue()))
    }

    @Test
    fun getCat(){
        val cat = newCatService.getCat()
        MatcherAssert.assertThat(cat, CoreMatchers.`is`(IsNull.notNullValue()))
    }

    @Test
    fun testCat(){
        val cat = newCatService.getCat()
        MatcherAssert.assertThat(cat._id, CoreMatchers.containsString("/"))
    }
}