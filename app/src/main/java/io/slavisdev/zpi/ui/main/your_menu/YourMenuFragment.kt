/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:55
 */

package io.slavisdev.zpi.ui.main.your_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentYourMenuBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.your_menu.YourMenuFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import javax.inject.Inject

class YourMenuFragment : Fragment(), YourMenuFragmentViewAccess {

    @Inject
    protected lateinit var model: YourMenuFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentYourMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(activity!!)
            .getAppComponent()
            .plus(YourMenuFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_your_menu,
            null,
            false
        )
        binding.apply {
            lifecycleOwner = this@YourMenuFragment
            model = this@YourMenuFragment.model
            viewAccess = this@YourMenuFragment
        }

        return binding.root
    }

}
