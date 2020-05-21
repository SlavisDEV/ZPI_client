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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import io.slavisdev.zpi.R
import io.slavisdev.zpi.adapter.RecipesAdapter
import io.slavisdev.zpi.databinding.FragmentBrowseRecipesBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.main.browse_recipes.BrowseRecipesFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import io.slavisdev.zpi.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class BrowseRecipesFragment : ScopedFragment(), BrowseRecipesFragmentViewAccess {

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

        model.setup()

        bindInfoUI()
        bindRecipesUI()

        return binding.root
    }

    private fun bindInfoUI() = launch {

        model.showInfoModal.observe(this@BrowseRecipesFragment, Observer {
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

        model.recipes.observe(this@BrowseRecipesFragment, Observer {
            if (it == null) return@Observer

            val recipes = it.filterNotNull()
            val adapter = RecipesAdapter(recipes, model)
            binding.recipesRecyclerView.adapter = adapter
        })
    }
}
