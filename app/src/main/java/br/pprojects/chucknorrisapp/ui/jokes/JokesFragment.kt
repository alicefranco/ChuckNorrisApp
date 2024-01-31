package br.pprojects.chucknorrisapp.ui.jokes

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
import br.pprojects.chucknorrisapp.databinding.FragmentJokesBinding
import br.pprojects.chucknorrisapp.ui.JokeAdapter
import br.pprojects.chucknorrisapp.util.createDialog
import br.pprojects.chucknorrisapp.util.gone
import br.pprojects.chucknorrisapp.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokesFragment : Fragment() {
    private val jokesViewModel: JokesViewModel by viewModel()
    private lateinit var adapter: JokeAdapter
    private val linearLayoutManager = LinearLayoutManager(context)
    private var openSearchFragment: () -> Unit = {}

    private var _binding: FragmentJokesBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentJokesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNoJokes.setOnClickListener {
            openSearchFragment()
        }

        jokesViewModel.searchMyJokes()

        jokesViewModel.getLoading().observe(viewLifecycleOwner) {
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

        jokesViewModel.getError().observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                context?.let { createDialog(it, getString(R.string.error), error) }
            }
        }

        jokesViewModel.getMyJokes().observe(viewLifecycleOwner) { jokes ->
            if (!jokes.isNullOrEmpty()) {
                binding.tvNoJokes.gone()
                setupRecycler()
                adapter.setJokes(jokes)
            }
        }

        setupRecycler()
    }

    private fun setupRecycler() {
        context?.let { adapter = JokeAdapter(it) }
        adapter.setShareClick(shareClick)
        binding.rvMyJokes.layoutManager = linearLayoutManager
        binding.rvMyJokes.adapter = adapter
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}