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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.slavisdev.zpi.R
import io.slavisdev.zpi.adapter.RecipesAdapter
import io.slavisdev.zpi.adapter.YourMenuRecipesAdapter
import io.slavisdev.zpi.databinding.FragmentYourMenuBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.your_menu.YourMenuFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import io.slavisdev.zpi.ui.base.ScopedFragment
import io.slavisdev.zpi.ui.main.MainActivity
import io.slavisdev.zpi.ui.main.favourite_recipes.FavouriteRecipesFragmentDirections
import kotlinx.coroutines.launch
import me.gujun.android.taggroup.TagGroup
import javax.inject.Inject

private const val RECIPES_VISIBLE_THRESHOLD = 5

class YourMenuFragment : ScopedFragment(), YourMenuFragmentViewAccess {

    @Inject
    protected lateinit var model: YourMenuFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentYourMenuBinding

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(requireActivity())
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

        model.setup()

        setOnTagClickListener()
        bindInfoUI()
        bindRecipesUI()
        bindAutoCompleteTextView()
        bindAvoidIngredients()

        return binding.root
    }

    private fun setOnTagClickListener() {
        binding.ingredientsTags.setOnTagClickListener {
            model.removeIngredient(it)
        }
    }

    private fun bindInfoUI() = launch {

        model.showInfoModal.observe(this@YourMenuFragment, Observer {
            if (it == null) return@Observer

            if (it == true) {
                val title = model.infoTitle.value ?: return@Observer
                val message = model.infoMessage.value ?: return@Observer
                showInfoModal(title, message) {}
            }
        })
    }

    private fun bindAutoCompleteTextView() = launch {

        model.ingredients.observe(this@YourMenuFragment, Observer {
            if (it == null) return@Observer

            val ingredients = it.map { ingredient ->
                ingredient.name()
            }
            val ingredientsAdapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, ingredients)

            binding.avoidIngredientsInput.apply {
                threshold = 2
                setAdapter(ingredientsAdapter)
                setOnItemClickListener { _, view, _, _ ->
                    binding.avoidIngredientsInput.text.clear()
                    val ingredientName = (view as TextView).text
                    val ingredient = it.find {  ingredient ->
                        ingredient.name() == ingredientName
                    } ?: return@setOnItemClickListener
                    model.addIngredient(ingredient)
                }
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
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                    val lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                    if (!isLoading && lastVisibleItem == totalItemCount - RECIPES_VISIBLE_THRESHOLD) {
                        isLoading = true
                        model.loadMoreRecipes()
                    }
                }
            })
        }

        model.recipes.observe(this@YourMenuFragment, Observer {
            if (it == null) return@Observer

            val recipes = it
            val adapter = YourMenuRecipesAdapter(recipes, model)
            binding.recipesRecyclerView.adapter = adapter
            isLoading = false
        })
    }

    override fun showLoadingScreen() {
        (activity as MainActivity).showLoadingScreen()
    }

    override fun hideLoadingScreen() {
        (activity as MainActivity).hideLoadingScreen()
    }

    private fun bindAvoidIngredients() = launch {
        model.avoidIngredients.observe(this@YourMenuFragment, Observer {
            if (it == null) return@Observer

            binding.ingredientsTags.setTags(it)
        })
    }

    override fun onRecipeClicked(recipeId: Int) {
        val action = YourMenuFragmentDirections
            .actionMainMenuYourMenuToRecipeFragment(recipeId)
        findNavController().navigate(action)
    }
}
