package com.example.employeetask.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.employeetask.R
import com.example.employeetask.model.Employee
import com.example.employeetask.viewmodel.EmployeeViewModel
import java.text.SimpleDateFormat
import java.util.Locale


class AddEmployee : Fragment() {

    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var etEmployeeName: EditText
    private lateinit var etEmployeeUsername : EditText
    private lateinit var etEmployeePassword: EditText
    private lateinit var btnSaveEmployee: Button



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_employee, container, false)
// Initialize Viewmodel
        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
// Bind UI elements
        etEmployeeName = view.findViewById(R.id.etEmployeeName)
        etEmployeeUsername = view.findViewById(R.id.etEmployeeUsername)
        etEmployeePassword = view.findViewById(R.id.etEmployeePassword)
        btnSaveEmployee = view.findViewById(R.id.btnSaveEmployee)

        // set up save button click listener
        btnSaveEmployee.setOnClickListener {
            saveEmployee()
            Toast.makeText(context, "succesfully added to databased", Toast.LENGTH_SHORT).show()
        }

        return view


    }

    private fun saveEmployee(){
        val name = etEmployeeName.text.toString().trim()
        val username = etEmployeeUsername.text.toString().trim()
        val password = etEmployeePassword.text.toString().trim()

        if(name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()){
            val sdfDate = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
            val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())





            val employee = Employee(
                name = name,
                username = username,
                password = password,
                latitude = 0.0,
                longitude = 0.0,
                address = ""
            )

            employeeViewModel.insert(employee)
            Log.d("AddEmployee", "Employee added: $employee")
        }
    }


}