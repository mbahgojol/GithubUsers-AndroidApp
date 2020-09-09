package com.blank.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
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
import com.blank.githubuser.utils.*
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
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> {
                    menu?.showSetting()
                    menu?.hideSearch()
                    menu?.hideFavorite()
                }
                R.id.searchFragment -> {
                    menu?.showSetting()
                    menu?.showSearch()
                    observerSearchUsername()
                }
                R.id.mainFragment -> {
                    menu?.showSetting()
                    menu?.showSearch()
                    searchView?.onActionViewCollapsed()
                }
                R.id.settingsFragment -> {
                    menu?.hideSearch()
                    menu?.hideSetting()
                }
                R.id.favoriteFragment -> {
                    menu?.showSearch()
                    menu?.showSetting()
                    menu?.hideFavorite()
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

    private fun observerSearchUsername() {
        navController.getBackStackEntry(R.id.searchFragment)
            .savedStateHandle
            .get<String>(SEARCH_USERS).let { username ->
                searchView?.setQuery(username, false)
            }
    }

    private fun settingSearchAfterConfiChange(menu: Menu) {
        when (navController.currentDestination?.id) {
            R.id.detailFragment -> {
                menu.hideSearch()
                menu.hideFavorite()
            }
            R.id.searchFragment -> {
                menu.showSearch()
                searchView?.onActionViewExpanded()
                observerSearchUsername()
            }
            R.id.mainFragment -> {
                menu.showSearch()
                searchView?.onActionViewCollapsed()
            }
            R.id.settingsFragment -> {
                menu.hideSearch()
                menu.hideSetting()
            }
            R.id.favoriteFragment -> {
                menu.hideFavorite()
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
            R.id.settingsFragment -> {
                navController.navigate(R.id.settingsFragment)
            }
            R.id.favoriteFragment -> {
                navController.navigate(R.id.favoriteFragment)
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
            if (navController.previousBackStackEntry?.destination?.id == R.id.searchFragment)
                searchView?.onActionViewExpanded()
        }
        return navController.navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleObserver)
    }
}