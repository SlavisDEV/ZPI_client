/*
 * Created by Sławomir Przybylski
 * 18/04/20 20:54
 */

package io.slavisdev.zpi.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.slavisdev.zpi.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ScopedFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected fun showInfoModal(title: Int, message: Int, action: () -> Unit) {
        InfoDialog(
            requireContext(),
            R.layout.modal_info,
            title,
            message
        ) {
            action()
        }.show()
    }
}