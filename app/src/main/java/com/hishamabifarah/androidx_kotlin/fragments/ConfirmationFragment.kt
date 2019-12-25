package com.hishamabifarah.androidx_kotlin.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hishamabifarah.androidx_kotlin.R

/**
 * A simple [Fragment] subclass.
 */
class ConfirmationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }


}
