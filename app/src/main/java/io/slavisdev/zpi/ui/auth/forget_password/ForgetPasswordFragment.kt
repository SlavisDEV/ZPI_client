/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:53
 */

package io.slavisdev.zpi.ui.auth.forget_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentForgetPasswordBinding
import io.slavisdev.zpi.databinding.FragmentRegisterBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.auth.forget_password.ForgetPasswordFragmentModule
import io.slavisdev.zpi.ui.base.InfoDialog
import io.slavisdev.zpi.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordFragment : ScopedFragment(), ForgetPasswordFragmentViewAccess {

    @Inject
    protected lateinit var model: ForgetPasswordFragmentViewModel

    private lateinit var binding: FragmentForgetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(requireActivity())
            .getAppComponent()
            .plus(ForgetPasswordFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forget_password,
            null,
            false
        )
        binding.apply {
            lifecycleOwner = this@ForgetPasswordFragment
            model = this@ForgetPasswordFragment.model
            viewAccess = this@ForgetPasswordFragment
        }

        bindInfoUI()

        return binding.root
    }

    private fun bindInfoUI() = launch {

        model.showInfoModal.observe(this@ForgetPasswordFragment, Observer {
            if (it == null) return@Observer

            if (it == true) {
                val title = model.infoTitle.value ?: return@Observer
                val message = model.infoMessage.value ?: return@Observer
                showInfoModal(title, message) {
                    model.clearFields()
                }
            }
        })
    }
}
