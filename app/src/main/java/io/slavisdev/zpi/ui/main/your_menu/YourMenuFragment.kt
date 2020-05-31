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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentYourMenuBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.your_menu.YourMenuFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import io.slavisdev.zpi.ui.base.ScopedFragment
import io.slavisdev.zpi.ui.main.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class YourMenuFragment : ScopedFragment(), YourMenuFragmentViewAccess {

    @Inject
    protected lateinit var model: YourMenuFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentYourMenuBinding

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

        bindInfoUI()
        bindAutoCompleteTextView()
        bindAvoidIngredients()

        return binding.root
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
}
