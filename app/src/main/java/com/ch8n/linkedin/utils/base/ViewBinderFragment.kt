package com.ch8n.linkedin.utils.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ch8n.linkedin.ui.router.Router

// checkout my view binding article to know in detail how viewbinding work
// https://chetangupta.net/viewbinding/
abstract class ViewBindingFragment<VB : ViewBinding> : Fragment() {

    abstract val TAG: String
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private var _router: Router? = null
    val router: Router get() = requireNotNull(_router)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _router = (context as? Router)
            ?: throw IllegalStateException("Activity needs to implement router")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun setup()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}