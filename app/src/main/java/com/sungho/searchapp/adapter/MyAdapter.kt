import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.ItemLoadingBinding
import com.sungho.searchapp.databinding.ItemSearchBinding
import com.sungho.searchapp.databinding.ItemSeparatorBinding
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.util.GlobalApplication
import com.sungho.searchapp.util.Layout
import java.lang.ClassCastException

class MyAdapter : RecyclerView.Adapter<MyAdapter.SearchViewHolder>(){
    var data = arrayListOf<SearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.SearchViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyAdapter.SearchViewHolder, position: Int) {
            holder.onBind(data[position])
    }

    override fun getItemCount(): Int = data.size
    override fun getItemId(position: Int): Long =position.toLong()


    fun setList(item: ArrayList<SearchItem>) {
        data.addAll(item)
    }

    fun removeItem(position : Int){
        data.removeAt(position)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder (val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : SearchItem){
            binding.item = data

            // 이미지
            Layout.roundCorner(binding.imageView,20)
            Glide.with(binding.view.context)
                .load(data.thumbnail)
                .into(binding.imageView)

            GlobalApplication.prefs.setLikeBtn(binding.likeBtn,true)
            binding.likeBtn.setOnClickListener {
                removeItem(position)
                GlobalApplication.prefs.removeLikeImg(binding.likeBtn,data)
            }
        }
    }
}