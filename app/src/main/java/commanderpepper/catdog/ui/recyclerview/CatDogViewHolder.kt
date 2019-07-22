package commanderpepper.catdog.ui.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import commanderpepper.catdog.databinding.CatdogItemBinding

class CatDogViewHolder(private val binding: CatdogItemBinding) : RecyclerView.ViewHolder(binding.root) {
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

    }
}