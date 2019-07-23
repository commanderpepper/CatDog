package commanderpepper.catdog.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.CatdoglistFragmentBinding
import commanderpepper.catdog.ui.recyclerview.CatDogAdapter
import commanderpepper.catdog.viewmodel.CatDogListFragmentViewModel
import kotlinx.android.synthetic.main.catdoglist_fragment.view.*

class CatDogListFragment : Fragment() {

    private lateinit var option: String
    private lateinit var listViewModel: CatDogListFragmentViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CatDogAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        option = activity?.intent?.extras?.getString("Option") ?: "Where's the data?"
        Log.d("ListFragment", option)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(CatDogListFragmentViewModel::class.java)
        listViewModel.option = option
        listViewModel.getUrls()

        Log.d("ViewModel", listViewModel.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.value!!.size.toString())
        Log.d("ViewModel", listViewModel.catDogUrls.value.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: CatdoglistFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.catdoglist_fragment, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = CatDogAdapter()


        // Whenever a change is made the MutableLiveData list, the list inside the view adapter is informed
        listViewModel.catDogUrls.observe(this, Observer {
            viewAdapter.submitList(it)
        })

        recyclerView = binding.root.catDogList.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    listViewModel.addUrls(6)
                }
            }
        })

        return binding.root
    }
}