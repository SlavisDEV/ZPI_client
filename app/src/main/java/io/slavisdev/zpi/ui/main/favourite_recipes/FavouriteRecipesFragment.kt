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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.slavisdev.zpi.R
import io.slavisdev.zpi.adapter.FavouriteRecipesAdapter
import io.slavisdev.zpi.adapter.RecipesAdapter
import io.slavisdev.zpi.databinding.FragmentFavouriteRecipesBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.favourite_recipes.FavouriteRecipesFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import io.slavisdev.zpi.ui.base.ScopedFragment
import io.slavisdev.zpi.ui.main.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteRecipesFragment : ScopedFragment(), FavouriteRecipesFragmentViewAccess {

    @Inject
    protected lateinit var model: FavouriteRecipesFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentFavouriteRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(requireActivity())
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

        model.setup()

        bindInfoUI()
        bindRecipesUI()

        return binding.root
    }

    private fun bindInfoUI() = launch {

        model.showInfoModal.observe(this@FavouriteRecipesFragment, Observer {
            if (it == null) return@Observer

            if (it == true) {
                val title = model.infoTitle.value ?: return@Observer
                val message = model.infoMessage.value ?: return@Observer
                showInfoModal(title, message) {}
            }
        })
    }

    private fun bindRecipesUI() = launch {
        binding.recipesRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        model.recipes.observe(this@FavouriteRecipesFragment, Observer {
            if (it == null) return@Observer

            val recipes = it
            val adapter = FavouriteRecipesAdapter(recipes, model)
            binding.recipesRecyclerView.adapter = adapter
        })
    }

    override fun showLoadingScreen() {
        (activity as MainActivity).showLoadingScreen()
    }

    override fun hideLoadingScreen() {
        (activity as MainActivity).hideLoadingScreen()
    }

    override fun onRecipeClicked(recipeId: Int) {
        val action = FavouriteRecipesFragmentDirections
            .actionMainMenuFavouriteRecipesToRecipeFragment(recipeId)
        findNavController().navigate(action)
    }
}
