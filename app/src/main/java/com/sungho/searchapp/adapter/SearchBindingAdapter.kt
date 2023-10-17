import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sungho.searchapp.model.SearchItem

object SearchBindingAdapter {
    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView : RecyclerView, items : ArrayList<SearchItem>){
        if(recyclerView.adapter == null){
            val adapter = SearchAdapter()
            adapter.setHasStableIds(true)
            recyclerView.adapter = adapter
        }

        val myAdapter = recyclerView.adapter as SearchAdapter

        myAdapter.data = items
        myAdapter.notifyDataSetChanged()    // notifyItemRangeInserted()로 수정하는게 바람직함
    }
}