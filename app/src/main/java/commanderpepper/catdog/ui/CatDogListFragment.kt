package commanderpepper.catdog.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.CatdoglistFragmentBinding

class CatDogListFragment : Fragment() {

    private lateinit var option: String

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        option = activity?.intent?.extras?.getString("Option") ?: "Where's the data?"
        Log.d("ListFragment", option)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CatdoglistFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.catdoglist_fragment, container, false)
        return binding.root
    }
}