package commanderpepper.catdog.ui.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import commanderpepper.catdog.R
import commanderpepper.catdog.viewmodel.CatDogListFragmentViewModel

class CatDogAdapter(val option: String, val viewModel: CatDogListFragmentViewModel) : ListAdapter<String, CatDogViewHolder>(StringDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatDogViewHolder {
        return CatDogViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.catdog_item,
                parent,
                false
            ),
            option,
            viewModel
        )
    }

    override fun onBindViewHolder(holder: CatDogViewHolder, position: Int) {
        getItem(position).let { url ->
            Log.d("ViewHolder", url)
            holder.bind(url)
            holder.setFavOnClickListener(url, position)
        }
    }

    override fun onViewAttachedToWindow(holder: CatDogViewHolder) {
        holder.setImageAndTag()
        super.onViewAttachedToWindow(holder)
    }

    private class StringDiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.compareTo(newItem) == 0
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
