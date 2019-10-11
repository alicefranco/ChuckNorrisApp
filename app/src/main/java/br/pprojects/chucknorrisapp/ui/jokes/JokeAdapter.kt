package br.pprojects.chucknorrisapp.ui

import br.pprojects.chucknorrisapp.data.model.Joke

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.pprojects.chucknorrisapp.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_joke.view.*

class JokeAdapter(private val context: Context) : RecyclerView.Adapter<JokeAdapter.ViewHolder>(){
    private var shareClick: (joke: Joke) -> Unit = {}
    private var jokes: List<Joke>? = null

    override fun getItemCount(): Int {
        return jokes?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joke, parent, false)
        return ViewHolder(view, context, shareClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = jokes?.get(position)
        joke?.let { itemJoke ->
            holder.itemView.setOnClickListener { shareClick(itemJoke) }
            holder.bind(itemJoke)
        }
    }

    fun setJokes(jokes: List<Joke>) {
        this.jokes  = jokes
    }

    fun setShareClick(itemClick: (joke: Joke) -> Unit) {
        this.shareClick = itemClick
    }

    class ViewHolder(item: View, val context: Context, val shareClick: (joke: Joke) -> Unit) : RecyclerView.ViewHolder(item) {
        private val jokeTextView: TextView = item.tv_joke
        private val jokeImageView: ImageView = item.iv_joke
        private val jokeCategory: TextView = item.tv_category
        private val shareImageView: ImageView = item.iv_share

        fun bind(joke: Joke) {

            Glide.with(context).load(joke.iconUrl).into(jokeImageView)

            var jokeCategoryList = ""

            if (joke.categories.isEmpty())
                jokeCategory.text = context.getString(R.string.uncategorized)
            else {
                joke.categories.forEach {
                    if(jokeCategoryList.isEmpty())
                        jokeCategoryList = it
                    else
                        jokeCategoryList = jokeCategoryList + "," + it
                }
                jokeCategory.text = jokeCategoryList
            }
            jokeTextView.text = joke.value

            shareImageView.setOnClickListener {
                shareClick(joke)
            }
        }
    }
}

