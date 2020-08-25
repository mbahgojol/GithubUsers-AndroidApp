package com.blank.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.blank.githubuser.R
import com.blank.githubuser.utils.SEARCH_USERS
import com.blank.githubuser.utils.visibilitySearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var searchView: SearchView? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.hostFragment)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.detailFragment -> {
                    menu?.visibilitySearch(false)
                }
                R.id.searchFragment -> {
                    menu?.visibilitySearch(true)
                    navController.getBackStackEntry(R.id.searchFragment)
                        .savedStateHandle
                        .get<String>(SEARCH_USERS).let { username ->
                            searchView?.setQuery(username, false)
                        }
                }
                R.id.mainFragment -> {
                    menu?.visibilitySearch(true)
                    searchView?.onActionViewCollapsed()
                }
            }
        }
        lifecycle.addObserver(lifecycleObserver)
    }

    private val lifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun resume() {
            menu?.let {
                settingSearchAfterConfiChange(it)
            }
        }
    }

    private fun settingSearchAfterConfiChange(menu: Menu) {
        when (navController.currentDestination?.id) {
            R.id.detailFragment -> {
                menu.visibilitySearch(false)
            }
            R.id.searchFragment -> {
                menu.visibilitySearch(true)
                searchView?.onActionViewExpanded()
                navController.getBackStackEntry(R.id.searchFragment)
                    .savedStateHandle
                    .get<String>(SEARCH_USERS).let { username ->
                        searchView?.setQuery(username, false)
                    }
            }
            R.id.mainFragment -> {
                menu.visibilitySearch(true)
                searchView?.onActionViewCollapsed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        this.menu = menu
        settingSearchAfterConfiChange(menu)

        searchView?.setOnSearchClickListener {
            if (navController.currentDestination?.id != R.id.detailFragment)
                navController.navigate(R.id.searchFragment)
        }

        searchView?.setOnCloseListener { true }
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.queryHint = resources.getString(R.string.search_hint)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                navController.getBackStackEntry(R.id.searchFragment)
                    .savedStateHandle
                    .set(SEARCH_USERS, query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu2 -> {
                Intent(Settings.ACTION_LOCALE_SETTINGS).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (navController.currentDestination?.id == R.id.searchFragment) {
            searchView?.onActionViewCollapsed()
            navController.getBackStackEntry(R.id.searchFragment)
                .savedStateHandle
                .remove<String>(SEARCH_USERS)
        } else if (navController.currentDestination?.id == R.id.detailFragment) {
            searchView?.onActionViewExpanded()
        }
        return navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleObserver)
    }
}