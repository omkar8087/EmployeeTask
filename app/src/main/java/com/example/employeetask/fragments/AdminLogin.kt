package com.example.employeetask.fragments


import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.employeetask.R
import com.example.employeetask.model.Employee
import com.example.employeetask.model.LoginHistory
import com.example.employeetask.viewmodel.EmployeeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Suppress("DEPRECATION")
class AdminLogin : Fragment() {
    private lateinit var employeeViewModel: EmployeeViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_admin_login, container, false)

        val usernameEditText = view.findViewById<EditText>(R.id.etAdminUsername)
        val passwordEditText = view.findViewById<EditText>(R.id.etAdminPassword)
        val loginButton = view.findViewById<Button>(R.id.btnAdminLogin)

        employeeViewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "admin" && password == "admin") {
                findNavController().navigate(R.id.action_adminLogin_to_adminDashboard)
            } else {
                checkEmployeeCredentials(username, password)

            }

        }
        return view
    }

    private fun checkEmployeeCredentials(username: String, password: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val employee = employeeViewModel.getEmployeeByUsernameAndPassword(username, password)
            withContext(Dispatchers.Main) {
                if (employee != null) {
                    getLocationAndSaveLoginDetails(employee)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Invalid credentials please try again",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    private fun getLocationAndSaveLoginDetails(employee: Employee) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

            if (location != null) {
                saveLocationAndNavigate(location, employee)


            } else {
                Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT)
                    .show()

            }
        }.addOnFailureListener {
//            Log.e("AdminLogin", "Failed to get location: ${e.message}")
            Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveLocationAndNavigate(location: Location, employee: Employee) {
        val latitude = location.latitude
        val longitude = location.longitude
        val address = getAddressFromLocation(latitude, longitude)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        Log.d("LocationUpdate", "Latitude: $latitude, Longitude: $longitude")

        // Update employee with new login details
        val updatedEmployee = employee.copy(
            latitude = latitude,
            longitude = longitude,
            date = date,
            time = time,
            address = address ?: "unable to fetch address"

        )

        // Create a new LoginHistory instance
        val loginHistory = LoginHistory(
            employeeId = updatedEmployee.id,
            date = date,
            time = time,
            address = address ?: "unable to fetch address"
        )





        findNavController().navigate(R.id.action_adminLogin_to_employeeDashboard)

        CoroutineScope(Dispatchers.IO).launch {
            employeeViewModel.insert(updatedEmployee)
            employeeViewModel.insertLoginHistory(loginHistory)
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.action_adminLogin_to_employeeDashboard)

            }
        }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            addresses?.let {

            }

            if (addresses != null && addresses.isNotEmpty()) {
                addresses[0].getAddressLine(0)

            } else {

                "Address not found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }


}





