package commanderpepper.catdog.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import commanderpepper.catdog.R
import commanderpepper.catdog.models.Url
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count

class CatDogRAdapter(
    private val parentScope: CoroutineScope,
    private val urls: List<Url>,
    private val saveFavUrl: (Url) -> (Unit),
    private val removeDavUrl: (Url) -> (Unit)
) :
    RecyclerView.Adapter<CatDogRViewHolder>() {

    private val job = SupervisorJob()
    private val recyclerScope = CoroutineScope(parentScope.coroutineContext + job)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatDogRViewHolder {
        return CatDogRViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.catdog_item,
                parent,
                false
            )
        )
    }

    @InternalCoroutinesApi
    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: CatDogRViewHolder, position: Int) {
        holder.setToNotFav()
        holder.setFavOnClickListener(
            urls[position],
            saveFavUrl,
            removeDavUrl
        )
    }

}