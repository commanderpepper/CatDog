package commanderpepper.catdogtwo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import commanderpepper.catdogtwo.CatDogConstants
import commanderpepper.catdogtwo.Choice
import commanderpepper.catdogtwo.R
import commanderpepper.catdogtwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set Content View
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        Log.d(CatDogConstants.dogTag, Choice.DOG.toString())

    }

    /**
     * Creates options inside the action bar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Goes to the favorite section when clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, ListActivity::class.java)
        when (item.itemId) {
            R.id.cat_nav -> {
                intent.putExtras(Bundle().apply { this.putString("Option", Choice.CATFAV.toString()) })
                startActivity(intent)
            }
            R.id.dog_nav -> {
                intent.putExtras(Bundle().apply { this.putString("Option", Choice.DOGFAV.toString()) })
                startActivity(intent)
            }
            else -> {  }
        }

        return super.onOptionsItemSelected(item)
    }

}
