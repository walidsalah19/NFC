package com.app.nfc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.nfc.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {
    private lateinit var mBinding: FragmentErrorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding= FragmentErrorBinding.inflate(inflater,container,false)


        return mBinding.root
    }
}