package com.example.employeetask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.employeetask.R

class HomePage : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        val adminLoginButton: Button = view.findViewById(R.id.adminLoginButton)
//        val employeeloginButton: Button = view.findViewById(R.id.employeeLoginButton)

        adminLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_adminLogin)
        }
//        employeeloginButton.setOnClickListener {
//            findNavController().navigate(R.id.action_homePage_to_employeeLogin)
//        }
        return view
    }


}