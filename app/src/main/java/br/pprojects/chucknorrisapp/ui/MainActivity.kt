package br.pprojects.chucknorrisapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.pprojects.chucknorrisapp.R
import br.pprojects.chucknorrisapp.databinding.ActivityMainBinding
import br.pprojects.chucknorrisapp.ui.search.SearchFragment
import br.pprojects.chucknorrisapp.ui.categories.CategoriesFragment
import br.pprojects.chucknorrisapp.ui.jokes.JokesFragment
import br.pprojects.chucknorrisapp.util.addFragment
import br.pprojects.chucknorrisapp.util.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)

        val fragment = JokesFragment()
        addFragment(fragment, R.id.fl_container, fragment.tag ?: "", true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.navigation_facts -> {
                val fragment = JokesFragment.newInstance(openSearchFragment)
                replaceFragment(fragment, R.id.fl_container, fragment.tag ?: JokesFragment.tag, true)
            }
            R.id.navigation_categories -> {
                val fragment = CategoriesFragment()
                replaceFragment(fragment, R.id.fl_container, fragment.tag ?: CategoriesFragment.tag, true)
            }
            R.id.navigation_search -> {
                val fragment = SearchFragment()
                replaceFragment(fragment, R.id.fl_container, fragment.tag ?: SearchFragment.tag, true)
            }
        }
        return true
    }

    private val openSearchFragment: () -> Unit = {
        val fragment = SearchFragment()
        replaceFragment(fragment, R.id.fl_container, fragment.tag ?: "", true)
    }
}
