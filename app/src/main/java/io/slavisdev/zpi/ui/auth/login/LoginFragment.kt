/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:56
 */

package io.slavisdev.zpi.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentLoginBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.auth.login.LoginFragmentModule
import io.slavisdev.zpi.navigation.Navigation
import io.slavisdev.zpi.ui.auth.AuthActivity
import io.slavisdev.zpi.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : ScopedFragment(), LoginFragmentViewAccess {

    @Inject
    protected lateinit var model: LoginFragmentViewModel

    @Inject
    protected lateinit var navigation: Navigation

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(requireActivity())
            .getAppComponent()
            .plus(LoginFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            null,
            false
        )
        binding.apply {
            lifecycleOwner = this@LoginFragment
            model = this@LoginFragment.model
            viewAccess = this@LoginFragment
        }

        bindInfoUI()

        return binding.root
    }

    private fun bindInfoUI() = launch {

        model.showInfoModal.observe(this@LoginFragment, Observer {
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

    override fun showRegisterScreen() {
        navigation.showRegisterFragment(activity as AuthActivity)
    }

    override fun showForgetPasswordScreen() {
        navigation.showForgotPasswordFragment(activity as AuthActivity)
    }

    override fun showMainActivity() {
        navigation.startMainActivity(requireActivity())
        requireActivity().finish()
    }

}
