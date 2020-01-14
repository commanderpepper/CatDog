package commanderpepper.catdog.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView(this, R.layout.activity_list) as ActivityListBinding

        val choice: String = intent?.extras?.getString("Option") ?: "Empty"
        Log.d("ListActivity", choice)
    }
}
