package br.pprojects.chucknorrisapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.pprojects.chucknorrisapp.R
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.databinding.FragmentSearchBinding
import br.pprojects.chucknorrisapp.ui.JokeAdapter
import br.pprojects.chucknorrisapp.util.createDialog
import br.pprojects.chucknorrisapp.util.gone
import br.pprojects.chucknorrisapp.util.hideKeyboard
import br.pprojects.chucknorrisapp.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(context)
    private lateinit var adapter: JokeAdapter

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        val tag = "JOKES_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { createDialog(it, getString(R.string.no_jokes_title), getString(R.string.no_jokes)) }

        binding.ivSearch.setOnClickListener {
            searchViewModel.searchJokeByCategory(binding.etSearch.text.toString())
            binding.etSearch.hideKeyboard()
        }

        binding.layoutFacts.setOnClickListener { binding.etSearch.hideKeyboard() }

        binding.ivDismiss.setOnClickListener {
            binding.etSearch.text.clear()
        }

        binding.fabRandomFact.setOnClickListener {
            searchViewModel.searchRandomJoke()
        }

        searchViewModel.getLoading().observe(viewLifecycleOwner) {
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

        searchViewModel.getError().observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                context?.let { createDialog(it, getString(R.string.error), error) }
            }
        }

        searchViewModel.getJoke().observe(viewLifecycleOwner) { joke ->
            setupRecycler()
            if (joke.value.isNotEmpty()) {
                adapter.setJokes(listOf(joke))
            }
        }

        setupRecycler()
    }

    private fun setupRecycler() {
        context?.let { adapter = JokeAdapter(it) }
        adapter.setShareClick(shareClick)
        binding.rvFacts.layoutManager = linearLayoutManager
        binding.rvFacts.adapter = adapter
    }

    private val shareClick: (joke: Joke) -> Unit = {
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.joke_subject))

        intent.putExtra(Intent.EXTRA_TEXT, it.url)

        activity?.packageManager?.let {
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}