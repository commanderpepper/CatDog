package commanderpepper.catdog.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.CatdoglistFragmentBinding
import commanderpepper.catdog.viewmodel.CatDogListFragmentViewModel

class CatDogListFragment : Fragment() {

    private lateinit var option: String
    lateinit var listViewModel: CatDogListFragmentViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        option = activity?.intent?.extras?.getString("Option") ?: "Where's the data?"
        Log.d("ListFragment", option)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(CatDogListFragmentViewModel::class.java)

        Log.d("ViewModel", listViewModel.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.value!!.size.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.value.toString())

        listViewModel.addCatUrl(2)

        Log.d("ViewModel", listViewModel.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.value!!.size.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.value.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CatdoglistFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.catdoglist_fragment, container, false)
        return binding.root
    }
}