package br.pprojects.chucknorrisapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.pprojects.chucknorrisapp.R
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.ui.JokeAdapter
import br.pprojects.chucknorrisapp.util.createDialog
import br.pprojects.chucknorrisapp.util.gone
import br.pprojects.chucknorrisapp.util.hideKeyboard
import br.pprojects.chucknorrisapp.util.visible
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(context)
    private lateinit var adapter: JokeAdapter

    companion object {
        val tag = "JOKES_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { createDialog(it, getString(R.string.no_jokes_title), getString(R.string.no_jokes)) }

        iv_search.setOnClickListener {
            searchViewModel.searchJokeByCategory(et_search.text.toString())
            et_search.hideKeyboard()
        }

        layout_facts.setOnClickListener { et_search.hideKeyboard() }

        iv_dismiss.setOnClickListener {
            et_search.text.clear()
        }

        fab_random_fact.setOnClickListener {
            searchViewModel.searchRandomJoke()
        }

        searchViewModel.getLoading().observe(this, Observer {
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

        searchViewModel.getError().observe(this, Observer { error ->
            if (error.isNotEmpty()) {
                context?.let { createDialog(it, getString(R.string.error), error) }
            }
        })

        searchViewModel.getJoke().observe(this, Observer { joke ->
            setupRecycler()
            if (!joke.value.isNullOrEmpty()) {
                adapter.setJokes(listOf(joke))
            }
        })

        setupRecycler()
    }

    private fun setupRecycler() {
        context?.let { adapter = JokeAdapter(it) }
        adapter.setShareClick(shareClick)
        rv_facts.layoutManager = linearLayoutManager
        rv_facts.adapter = adapter
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
}