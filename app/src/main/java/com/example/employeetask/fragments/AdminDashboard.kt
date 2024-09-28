package com.example.employeetask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeetask.R
import com.example.employeetask.adapter.EmployeeAdapter
import com.example.employeetask.model.Employee
import com.example.employeetask.viewmodel.EmployeeViewModel

class AdminDashboard : Fragment() {

    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var employeeAdapter: EmployeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)


        val toolbarButton: ImageButton = view.findViewById(R.id.toolbarButton)
        toolbarButton.setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboard_to_addEmployee)
        }

        // Initialize recyclerview
        recyclerView = view.findViewById(R.id.recyclerViewEmployees)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        employeeAdapter = EmployeeAdapter(emptyList()) { employeeToDelete ->
            deleteEmployee(employeeToDelete)

        }
        recyclerView.adapter = employeeAdapter

        // Initialize Viewmodel
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        // Observe the liveData from viewModel
        employeeViewModel.allEmployees.observe(viewLifecycleOwner) { employeeList ->
            employeeAdapter.setEmployees(employeeList)
            loadLoginHistory(employeeList)
        }


        return view
    }

    private fun loadLoginHistory(employeeList: List<Employee>) {
        employeeList.forEach { employee ->
            employeeViewModel.getLoginHistoryByEmployeeId(employee.id).observe(viewLifecycleOwner) { historyList ->
                employee.history = historyList // Set the history directly
                employeeAdapter.notifyDataSetChanged()
            }
        }
    }



    private fun deleteEmployee(employee: Employee) {
        // Implement the delete functionality here
    }
}