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

//todo notes: implement button click inside Fragment
    // implements View.OnClickListener interface
    // implement onClick() for OnClickListener interface
    // define navController, ':' means of type NavController ,'?' means can be null
    // instantiate navController when view is created => override onViewCreated method

class MainFragment : Fragment() , View.OnClickListener {

    // navController can be declared like so :
    // lateinit var navController : NavController
    // then no need to use '!!' to assert not null because navController will become
    // non null receiver when we specify lateinit var modifier
    // check SpecifyAmountFragment.kt

     var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // navController has  a reference to the view (fragment_main) which exists inside navigation graph
        // now navController has a reference to the navigation graph
        navController = Navigation.findNavController(view)

        // attach onClickListeners to all buttons inside this view
        view.findViewById<Button>(R.id.view_transactions_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.send_money_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.view_balance_btn).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            // on view_transactions_btn click, navController is set to navigate to action specified in nav_graph.xml
            R.id.view_transactions_btn -> navController!! .navigate(R.id.action_mainFragment_to_viewTransactionFragment)

            R.id.send_money_btn -> navController!! .navigate(R.id.action_mainFragment_to_chooseRecipientFragment)
            R.id.view_balance_btn -> navController!! .navigate(R.id.action_mainFragment_to_viewBalanceFragment)
        }
    }
}
