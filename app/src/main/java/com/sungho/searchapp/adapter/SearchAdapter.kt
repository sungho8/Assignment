import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungho.searchapp.databinding.ItemSearchBinding
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.util.Layout

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    var data = listOf<SearchItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.apply {
            holder.onBind(data[position])
        }
    }

    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long =position.toLong()


    inner class SearchViewHolder (val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : SearchItem){
            binding.item = data
            //테두리 둥글게
            Layout.roundCorner(binding.imageView,20)

            Glide.with(binding.view.context)
                .load(data.thumbnail)
                .into(binding.imageView)
        }
    }


}