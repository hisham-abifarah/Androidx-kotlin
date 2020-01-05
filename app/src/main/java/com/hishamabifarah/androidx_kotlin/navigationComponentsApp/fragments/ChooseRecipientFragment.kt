package com.hishamabifarah.androidx_kotlin.navigationComponentsApp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.hishamabifarah.androidx_kotlin.R

class ChooseRecipientFragment : Fragment() , View.OnClickListener {
    var navController:NavController ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_recipient, container, false)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.next_btn ->{
                navController!!.navigate(R.id.action_chooseRecipientFragment_to_specifyAmountFragment)
            }
            R.id.cancel_btn -> activity!!.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.next_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.cancel_btn).setOnClickListener(this)
    }
}
