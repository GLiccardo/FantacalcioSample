package it.fantacalcio.sample.feature_list.presentation

import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.extension.replaceFragment
import it.fantacalcio.sample.core.ui.base.BaseActivity
import it.fantacalcio.sample.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<PlayersListViewModel, ActivityMainBinding>(
    R.layout.activity_main,
    PlayersListViewModel::class.java
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