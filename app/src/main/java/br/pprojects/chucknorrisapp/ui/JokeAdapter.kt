package br.pprojects.chucknorrisapp.ui

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pprojects.chucknorrisapp.R
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.databinding.ItemJokeBinding

class JokeAdapter(private val context: Context) : RecyclerView.Adapter<JokeAdapter.ViewHolder>() {
    private var shareClick: (joke: Joke) -> Unit = {}
    private var jokes: List<Joke>? = null

    override fun getItemCount(): Int {
        return jokes?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJokeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, context, shareClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = jokes?.get(position)
        joke?.let { itemJoke ->
            holder.itemView.setOnClickListener { shareClick(itemJoke) }
            holder.bind(itemJoke)
        }
    }

    fun setJokes(jokes: List<Joke>) {
        this.jokes = jokes
    }

    fun setShareClick(itemClick: (joke: Joke) -> Unit) {
        this.shareClick = itemClick
    }

    class ViewHolder(
        val binding: ItemJokeBinding,
        val context: Context,
        val shareClick: (joke: Joke) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(joke: Joke) {

            var jokeCategoryList = ""

            joke.categories.forEach {
                if (jokeCategoryList.isEmpty())
                    jokeCategoryList = it
                else
                    jokeCategoryList = jokeCategoryList + "," + it
            }
            binding.tvCategory.text = jokeCategoryList
            binding.tvJoke.text = joke.value
            if (joke.largeJoke)
                binding.tvJoke.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.smaller_font)
                )
            else
                binding.tvJoke.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.bigger_font)
                )

            binding.ivShare.setOnClickListener {
                shareClick(joke)
            }
        }
    }
}
