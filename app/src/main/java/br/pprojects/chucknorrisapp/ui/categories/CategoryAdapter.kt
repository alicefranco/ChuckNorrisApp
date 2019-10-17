package br.pprojects.chucknorrisapp.ui.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.pprojects.chucknorrisapp.R
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var categories: List<String>? = null

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view, context)
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

    class ViewHolder(item: View, val context: Context) : RecyclerView.ViewHolder(item) {
        private val categoryTextView: TextView = item.tv_category

        fun bind(category: String) {
            categoryTextView.text = category
        }
    }
}
