package commanderpepper.catdog.ui.recyclerview

import android.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import commanderpepper.catdog.databinding.CatdogItemBinding
import commanderpepper.catdog.repo.DatabaseLocalSource
import commanderpepper.catdog.viewmodel.CatDogListFragmentViewModel

class CatDogViewHolder(
    private val binding: CatdogItemBinding,
    val option: String,
    val viewModel: CatDogListFragmentViewModel
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(url: String) {
        if (url.contains("gif".toRegex())) {
            Glide.with(binding.root)
                .asGif()
                .load(url)
                .into(binding.catdogImage)
        } else {
            Glide.with(binding.root)
                .load(url)
                .into(binding.catdogImage)
        }
        setImageAndTag()
    }

    fun setImageAndTag() {
        if (option.contains("FAV")) {
            binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_on))
            binding.favImage.tag = "ON"
        } else {
            binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_off))
            binding.favImage.tag = "OFF"
        }
    }

    fun setFavOnClickListener(url: String, position: Int) {
//        val localSource = DatabaseLocalSource.getInstance(this.itemView.context)
        binding.favImage.setOnClickListener {
            if (binding.favImage.tag == "OFF") {
                viewModel.saveFavoriteUrl(url, position)
                binding.favImage.setImageDrawable(itemView.resources.getDrawable(android.R.drawable.btn_star_big_on))
                binding.favImage.tag = "ON"

            } else {
                viewModel.deleteFavoriteUrl(url, position)
                binding.favImage.setImageDrawable(itemView.resources.getDrawable(android.R.drawable.btn_star_big_off))
                binding.favImage.tag = "OFF"

//                when (option) {
//                    "CAT" -> localSource!!.deleteCat(url)
//                    "DOG" -> localSource!!.deleteDog(url)
//                    "CATFAV" -> localSource!!.deleteCat(url)
//                    "DOGFAV" -> localSource!!.deleteDog(url)
//                }
//                binding.favImage.setImageDrawable(itemView.resources.getDrawable(android.R.drawable.btn_star_big_off))
//                binding.favImage.tag = "OFF"
            }
        }
    }
}