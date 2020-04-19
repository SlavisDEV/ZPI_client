/*
 * Created by Sławomir Przybylski
 * 19/04/20 20:59
 */

package io.slavisdev.zpi.ui.main.favourite_recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentFavouriteRecipesBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.favourite_recipes.FavouriteRecipesFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import javax.inject.Inject

class FavouriteRecipesFragment : Fragment(), FavouriteRecipesFragmentViewAccess {

    @Inject
    protected lateinit var model: FavouriteRecipesFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentFavouriteRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(activity!!)
            .getAppComponent()
            .plus(FavouriteRecipesFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favourite_recipes,
            null,
            false
        )
        binding.apply {
            lifecycleOwner = this@FavouriteRecipesFragment
            model = this@FavouriteRecipesFragment.model
            viewAccess = this@FavouriteRecipesFragment
        }

        return binding.root
    }

}
