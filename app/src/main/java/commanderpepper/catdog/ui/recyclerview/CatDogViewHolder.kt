package commanderpepper.catdog.ui.recyclerview

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.CatdogItemBinding
import commanderpepper.catdog.repo.DatabaseLocalSource

class CatDogViewHolder(private val binding: CatdogItemBinding, val option: String) :
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
        if (option.contains("FAV")) {
            binding.favImage.setImageDrawable(itemView.resources.getDrawable(android.R.drawable.btn_star_big_on))
            binding.favImage.tag = "ON"
        }
    }

    fun setFavOnClickListener(url: String) {
        val s = DatabaseLocalSource.getInstance(this.itemView.context)
        binding.favImage.setOnClickListener {
            if (binding.favImage.tag == "OFF") {
                when (option) {
                    "CAT" -> s!!.addCat(url)
                    "DOG" -> s!!.addDog(url)
                    "CATFAV" -> s!!.addCat(url)
                    "DOGFAV" -> s!!.addDog(url)
                }
                binding.favImage.setImageDrawable(itemView.resources.getDrawable(android.R.drawable.btn_star_big_on))
                binding.favImage.tag = "ON"
            } else {
                when (option) {
                    "CAT" -> s!!.deleteCat(url)
                    "DOG" -> s!!.deleteDog(url)
                    "CATFAV" -> s!!.deleteCat(url)
                    "DOGFAV" -> s!!.deleteDog(url)
                }
                binding.favImage.setImageDrawable(itemView.resources.getDrawable(android.R.drawable.btn_star_big_off))
                binding.favImage.tag = "OFF"
            }
        }
    }
}