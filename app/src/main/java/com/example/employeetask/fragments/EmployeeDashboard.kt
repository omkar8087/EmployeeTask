package com.example.employeetask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.employeetask.R


class EmployeeDashboard : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_employee_dashboard, container, false)

        val btnlogout: Button = view.findViewById(R.id.btnEmployeelogout)
        btnlogout.setOnClickListener {
            findNavController().navigate(R.id.action_employeeDashboard_to_homePage)
        }

        return view
    }


}