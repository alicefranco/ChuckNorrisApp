package br.pprojects.chucknorrisapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.pprojects.chucknorrisapp.R
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.databinding.FragmentCategoriesBinding
import br.pprojects.chucknorrisapp.util.createDialog
import br.pprojects.chucknorrisapp.util.gone
import br.pprojects.chucknorrisapp.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {
    private val categoriesViewModel: CategoriesViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(context)
    private lateinit var adapter: CategoryAdapter

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    companion object {
        val tag = "CATEGORIES_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        categoriesViewModel.searchCategories()

        categoriesViewModel.getLoading().observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.DONE,
                NetworkState.ERROR -> {
                    binding.loadingLayout.gone()
                }
                NetworkState.LOADING -> {
                    binding.loadingLayout.visible()
                }
                else -> {}
            }
        }

        categoriesViewModel.getError().observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                context?.let { createDialog(it, getString(R.string.error), error) }
            }
        }

        categoriesViewModel.getCategories().observe(viewLifecycleOwner) { categories ->
            setupRecycler()
            if (!categories.isNullOrEmpty()) {
                adapter.setCategories(categories)
            }
        }

        setupRecycler()
    }

    private fun setupRecycler() {
        context?.let { adapter = CategoryAdapter(it) }
        binding.rvCategories.layoutManager = linearLayoutManager
        binding.rvCategories.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}