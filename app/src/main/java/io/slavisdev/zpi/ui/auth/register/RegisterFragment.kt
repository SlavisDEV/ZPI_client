/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:57
 */

package io.slavisdev.zpi.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import io.slavisdev.zpi.R
import io.slavisdev.zpi.databinding.FragmentRegisterBinding
import io.slavisdev.zpi.di.base.App
import io.slavisdev.zpi.di.ui.auth.register.RegisterFragmentModule
import io.slavisdev.zpi.ui.base.ScopedFragment
import javax.inject.Inject

class RegisterFragment : ScopedFragment(), RegisterFragmentViewAccess {

    @Inject
    protected lateinit var model: RegisterFragmentViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        App.get(activity!!)
            .getAppComponent()
            .plus(RegisterFragmentModule(this))
            .inject(this)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            null,
            false
        )
        binding.apply {
            lifecycleOwner = this@RegisterFragment
            model = this@RegisterFragment.model
            viewAccess = this@RegisterFragment
        }

        return binding.root
    }

}
