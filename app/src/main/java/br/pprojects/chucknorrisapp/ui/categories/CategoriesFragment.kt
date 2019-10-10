package br.pprojects.chucknorrisapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.pprojects.chucknorrisapp.R
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.ui.CategoryAdapter
import br.pprojects.chucknorrisapp.util.createDialog
import br.pprojects.chucknorrisapp.util.gone
import br.pprojects.chucknorrisapp.util.visible
import kotlinx.android.synthetic.main.fragment_categories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {
    private val categoriesViewModel: CategoriesViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter

    companion object {
        val tag = "CATEGORIES_FRAGMENTa"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        categoriesViewModel.searchCategories()

        categoriesViewModel.getLoading().observe(this, Observer {
            when (it) {
                NetworkState.DONE,
                NetworkState.NO_CONNECTION,
                NetworkState.ERROR -> {
                    loading_layout.gone()
                }
                NetworkState.LOADING -> {
                    loading_layout.visible()
                }
            }
        })

        categoriesViewModel.getError().observe(this, Observer { error ->
            if (error.isNotEmpty()) {
                context?.let { createDialog(it, getString(R.string.error), error) }
            }
        })

        categoriesViewModel.getCategories().observe(this, Observer { categories ->
            setupRecycler()
            if (!categories.isNullOrEmpty()) {
                adapter.setCategories(categories)
            }
        })

        setupRecycler()
    }

    private fun setupRecycler() {
        context?.let { adapter = CategoryAdapter(it) }
        rv_categories.layoutManager = linearLayoutManager
        rv_categories.adapter = adapter
    }
}