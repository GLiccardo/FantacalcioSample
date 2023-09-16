package it.fantacalcio.sample.core.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<VM : ViewModel, VB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding : VB

    open fun doInOnCreate() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        doInOnCreate()
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, layoutResId)
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

}