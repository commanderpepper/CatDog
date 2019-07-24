package commanderpepper.catdog.ui

import android.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.Choice
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set Content View
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        Log.d(CatDogConstants.dogTag, Choice.DOG.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.cat_nav -> Toast.makeText(applicationContext, "Cat", Toast.LENGTH_SHORT).show()
            R.id.dog_nav -> Toast.makeText(applicationContext, "Dog", Toast.LENGTH_SHORT).show()
            R.id.both_nav -> Toast.makeText(applicationContext, "Both", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

}
