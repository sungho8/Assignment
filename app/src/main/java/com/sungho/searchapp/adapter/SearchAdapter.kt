import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungho.searchapp.databinding.ItemLoadingBinding
import com.sungho.searchapp.databinding.ItemSearchBinding
import com.sungho.searchapp.databinding.ItemSeparatorBinding
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.util.Layout
import java.lang.ClassCastException

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val LOADING_BAR = -1
    val SEARCH_ITEM = 0
    val SEPARATOR = 1

    var data = arrayListOf<SearchItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            SEARCH_ITEM->{
                val binding = ItemSearchBinding.inflate(
                    LayoutInflater.from(parent.context), parent,false)
                SearchViewHolder(binding)
            }
            SEPARATOR->{
                val binding = ItemSeparatorBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                )
                SeparatorViewHolder(binding)
            }
            LOADING_BAR->{
                val binding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false
                )
                LoadingViewHolder(binding)
            }
            else -> {
                throw ClassCastException("Unknown viewType $viewType")
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> {
                holder.onBind(data[position])
            }
            is SeparatorViewHolder -> {
                holder.onBind(data[position])
            }
            is LoadingViewHolder -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long =position.toLong()


    fun setList(notice: ArrayList<SearchItem>,page : Int) {
        data.add(SearchItem(type = SEPARATOR,title = "page $page"))
        data.addAll(notice)
        data.add(SearchItem(type = LOADING_BAR)) // progress bar
    }
    fun deleteLoading(){
        data.removeAt(data.lastIndex)
    }

    inner class SearchViewHolder (val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : SearchItem){
            binding.item = data
            binding.title.text = "${position} ${binding.title.text}"
            //테두리 둥글게
            Layout.roundCorner(binding.imageView,20)

            Glide.with(binding.view.context)
                .load(data.thumbnail)
                .into(binding.imageView)
        }
    }

    inner class SeparatorViewHolder(val binding : ItemSeparatorBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : SearchItem){
            binding.pageNumber.text = data.title
        }
    }
    inner class LoadingViewHolder(val binding : ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root){}
}