package br.pprojects.chucknorrisapp.ui.jokes

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
import br.pprojects.chucknorrisapp.util.visible
import kotlinx.android.synthetic.main.fragment_search.loading_layout
import kotlinx.android.synthetic.main.fragment_jokes.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokesFragment : Fragment() {
    private val jokesViewModel: JokesViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(context)
    private lateinit var adapter: JokeAdapter
    private var openSearchFragment: () -> Unit = {}

    companion object {
        val tag = "MY_JOKES_FRAGMENT"
        fun newInstance(openSearchFragment: () -> Unit): JokesFragment {
            return JokesFragment().apply {
                this.openSearchFragment = openSearchFragment
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_no_jokes.setOnClickListener {
            openSearchFragment()
        }

        jokesViewModel.searchMyJokes()

        jokesViewModel.getLoading().observe(this, Observer {
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

        jokesViewModel.getError().observe(this, Observer { error ->
            if (error.isNotEmpty()) {
                context?.let { createDialog(it, getString(R.string.error), error) }
            }
        })

        jokesViewModel.getMyJokes().observe(this, Observer { jokes ->
            if (!jokes.isNullOrEmpty()) {
                tv_no_jokes.gone()
                setupRecycler()
                adapter.setJokes(jokes)
            }
        })

        setupRecycler()
    }

    private fun setupRecycler() {
        context?.let { adapter = JokeAdapter(it) }
        adapter.setShareClick(shareClick)
        rv_my_jokes.layoutManager = linearLayoutManager
        rv_my_jokes.adapter = adapter
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