package it.fantacalcio.sample.feature_list.presentation

import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.extension.replaceFragment
import it.fantacalcio.sample.core.ui.base.BaseActivity
import it.fantacalcio.sample.databinding.ActivityMainBinding
import it.fantacalcio.sample.feature_list.presentation.players_list.PlayersListFragment

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    R.layout.activity_main,
    MainViewModel::class.java
) {

    override fun doInOnCreate() {
        super.doInOnCreate()

        setupNavigationView()
    }

    private fun setupNavigationView() {
        with(binding) {
            // Use Navigation
//            val navController = findNavController(R.id.container)
//            bottomNavigationView.setupWithNavController(navController)

            // Use fragmentManager
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_bar_list -> {
                        replaceFragment(
                            PlayersListFragment.newInstance("pagina 1"),
                            PlayersListFragment.TAG,
                            containerResId = R.id.container
                        )
                        true
                    }

                    R.id.navigation_bar_preferred -> {
                        replaceFragment(
                            PlayersListFragment.newInstance("pagina 2"),
                            PlayersListFragment.TAG,
                            containerResId = R.id.container
                        )
                        true
                    }

                    else -> false
                }
            }

            bottomNavigationView.itemIconTintList = null
            bottomNavigationView.selectedItemId = R.id.navigation_bar_list
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}