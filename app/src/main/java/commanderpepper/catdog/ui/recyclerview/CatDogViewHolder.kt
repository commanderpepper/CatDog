package commanderpepper.catdog.ui.recyclerview

import android.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import commanderpepper.catdog.databinding.CatdogItemBinding
import commanderpepper.catdog.viewmodel.CatDogListFragmentViewModel

class CatDogViewHolder(
    private val binding: CatdogItemBinding,
    val option: String,
    val viewModel: CatDogListFragmentViewModel
) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Used to bind the url properly. Some urls direct to gifs and those need to be properly dealt with.
     */
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

    /**
     * If the option the user has chosen a FAV, then set all the fav stars to be on. Otherwise set them to be off.
     */
    fun setImageAndTag() {
        if (option.contains("FAV")) {
            binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_on))
            binding.favImage.tag = "ON"
        } else {
            binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_off))
            binding.favImage.tag = "OFF"
        }
    }

    /**
     * Contains logic to deal with user saving and unsaving urls. Calls the ViewModel.
     */
//    fun setFavOnClickListener(url: String, position: Int) {
//        binding.favImage.setOnClickListener {
//            if (binding.favImage.tag == "OFF") {
//                viewModel.saveFavoriteUrl(url, position)
//                binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_on))
//                binding.favImage.tag = "ON"
//
//            } else {
//                viewModel.deleteFavoriteUrl(url, position)
//                binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_off))
//                binding.favImage.tag = "OFF"
//            }
//        }
//    }
}