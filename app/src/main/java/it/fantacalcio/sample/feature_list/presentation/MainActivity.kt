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
        replaceFragment(
            PlayersListFragment.newInstance("something"),
            PlayersListFragment.TAG,
            containerResId = R.id.container
        )
    }

}