/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:57
 */

package io.slavisdev.zpi.ui.main.browse_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentBrowseRecipesBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.browse_recipes.BrowseRecipesFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import javax.inject.Inject

class BrowseRecipesFragment : Fragment(), BrowseRecipesFragmentViewAccess {

    @Inject
    protected lateinit var model: BrowseRecipesFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentBrowseRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(activity!!)
            .getAppComponent()
            .plus(BrowseRecipesFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_browse_recipes,
            null,
            false
        )
        binding.apply {
            lifecycleOwner = this@BrowseRecipesFragment
            model = this@BrowseRecipesFragment.model
            viewAccess = this@BrowseRecipesFragment
        }

        return binding.root
    }

}
