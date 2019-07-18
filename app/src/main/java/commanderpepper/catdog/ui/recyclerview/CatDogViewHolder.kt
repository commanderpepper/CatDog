package commanderpepper.catdog.ui.recyclerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CatDogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val i = itemView.also {
        it.hasOnClickListeners()
        it.setOnClickListener {
            Log.d("OnClickListener", it.toString())
        }
    }

}