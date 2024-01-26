package br.pprojects.chucknorrisapp.ui.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pprojects.chucknorrisapp.databinding.ItemCategoryBinding

class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var categories: List<String>? = null

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories?.get(position)
        category?.let { itemCategory ->
            holder.bind(itemCategory)
        }
    }

    fun setCategories(categories: List<String>) {
        this.categories = categories
    }

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.tvCategory.text = category
        }
    }
}
