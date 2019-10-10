package br.pprojects.chucknorrisapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.pprojects.chucknorrisapp.ui.JokesFragment
import br.pprojects.chucknorrisapp.util.addFragment
import br.pprojects.chucknorrisapp.util.createDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener(this)

        createDialog(this, "Hi, there!", getString(R.string.no_facts))

        val fragment = JokesFragment()
        addFragment(fragment, R.id.fl_container, fragment.tag ?: "", true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.navigation_facts -> {
                val fragment = JokesFragment()
                addFragment(fragment, R.id.fl_container, fragment.tag ?: "", true)
            }
            R.id.navigation_categories -> {
            }
            R.id.navigation_my_facts -> {
            }
        }
        return true
    }

}
