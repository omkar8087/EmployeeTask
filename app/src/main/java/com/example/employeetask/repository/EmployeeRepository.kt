package com.example.employeetask.repository

import androidx.lifecycle.LiveData
import com.example.employeetask.database.EmployeeDao
import com.example.employeetask.model.Employee
import com.example.employeetask.model.LoginHistory

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    val  allEmployees: LiveData<List<Employee>> = employeeDao.getAllEmployees()

    suspend fun insert(employee: Employee){
        employeeDao.insert(employee)
    }

    suspend fun updateEmployee(employee: Employee){
        employeeDao.updateEmployee(employee)
    }

    suspend fun deleteAll() {
        employeeDao.deleteAllEmployees()
    }

    suspend fun deleteEmployee(employee: Employee) {
        employeeDao.deleteEmployeeById(employee.id)
    }




    suspend fun getEmployeeByUsernameAndPassword(username: String, password: String): Employee? {
        return employeeDao.getEmployeeByUsernameAndPassword(username, password)
    }

    suspend fun insertLoginHistory(loginHistory: LoginHistory){
        employeeDao.insertLoginHistory(loginHistory)
    }

      fun getLoginHistoryByEmployeeId(employeeId: Int): LiveData<List<LoginHistory>> {
        return employeeDao.getLoginHistoryByEmployeeId(employeeId)
    }
}