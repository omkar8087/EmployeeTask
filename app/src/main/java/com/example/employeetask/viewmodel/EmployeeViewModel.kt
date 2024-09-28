package com.example.employeetask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.employeetask.database.EmployeeDatabase
import com.example.employeetask.model.Employee
import com.example.employeetask.model.LoginHistory
import com.example.employeetask.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel (application: Application) : AndroidViewModel(application){
    private val repository: EmployeeRepository
    val  allEmployees: LiveData<List<Employee>>


    init {
        val employeeDao = EmployeeDatabase.getDatabase(application).employeeDao()
        repository = EmployeeRepository(employeeDao)
        allEmployees = repository.allEmployees
    }

    fun insert(employee: Employee) = viewModelScope.launch {
        repository.insert(employee)

    }

    fun updateEmployee(employee: Employee) = viewModelScope.launch {
        repository.updateEmployee(employee)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteEmployee(employee: Employee)= viewModelScope.launch {
        repository.deleteEmployee(employee)
    }

    suspend fun getEmployeeByUsernameAndPassword(username: String, password: String): Employee? {
        return withContext(Dispatchers.IO) {
            repository.getEmployeeByUsernameAndPassword(username, password)
        }
    }

    fun insertLoginHistory(loginHistory: LoginHistory){
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertLoginHistory(loginHistory)
        }
    }

      fun getLoginHistoryByEmployeeId(employeeId: Int): LiveData<List<LoginHistory>> {
        return repository.getLoginHistoryByEmployeeId(employeeId)
    }
}