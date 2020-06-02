/*
 * Created by Sławomir Przybylski
 * 26/05/20 09:37
 */

package io.slavisdev.zpi.ui.recpie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import io.slavisdev.zpi.R
import io.slavisdev.zpi.adapter.IngredientsAdapter
import io.slavisdev.zpi.adapter.MethodAdapter
import io.slavisdev.zpi.databinding.FragmentRecipeBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.recipe.RecipeFragmentModule
import io.slavisdev.zpi.ui.base.ScopedFragment
import io.slavisdev.zpi.ui.main.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject


class RecipeFragment : ScopedFragment(), RecipeFragmentViewAccess {

    @Inject
    protected lateinit var model: RecipeFragmentViewModel

    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(requireActivity())
            .getAppComponent()
            .plus(RecipeFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, null, false)
        binding.apply {
            lifecycleOwner = this@RecipeFragment
            model = this@RecipeFragment.model
            viewAccess = this@RecipeFragment
        }

        val safeArgs = arguments?.let {
            RecipeFragmentArgs.fromBundle(it)
        }

        model.setup(safeArgs?.recipeId)

        bindIngredientsUI()
        bindMethodsUI()
        bindInfoUI()

        return binding.root
    }

    private fun bindIngredientsUI() = launch {
        binding.ingredients.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        model.ingredients.observe(this@RecipeFragment, Observer {
            if (it == null) return@Observer

            val adapter = IngredientsAdapter(it)
            binding.ingredients.adapter = adapter
        })
    }

    private fun bindMethodsUI() = launch {
        binding.method.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        model.methods.observe(this@RecipeFragment, Observer {
            if (it == null) return@Observer

            val adapter = MethodAdapter(it)
            binding.method.adapter = adapter
        })
    }

    private fun bindInfoUI() = launch {

        model.showInfoModal.observe(this@RecipeFragment, Observer {
            if (it == null) return@Observer

            if (it == true) {
                val title = model.infoTitle.value ?: return@Observer
                val message = model.infoMessage.value ?: return@Observer
                showInfoModal(title, message) {}
            }
        })
    }

    override fun showLoadingScreen() {
        (activity as MainActivity).showLoadingScreen()
    }

    override fun hideLoadingScreen() {
        (activity as MainActivity).hideLoadingScreen()
    }
}
