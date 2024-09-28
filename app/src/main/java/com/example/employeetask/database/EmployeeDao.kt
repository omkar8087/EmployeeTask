package com.example.employeetask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.employeetask.model.Employee
import com.example.employeetask.model.LoginHistory

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: Employee): Long

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table ORDER BY id ASC")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Query("DELETE FROM employee_table")
    fun deleteAllEmployees(): Int

    @Query("DELETE FROM employee_table WHERE id = :employeeId")
    suspend fun deleteEmployeeById(employeeId: Int): Int

    @Query("SELECT * FROM employee_table WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getEmployeeByUsernameAndPassword(username: String, password: String): Employee?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginHistory(loginHistory: LoginHistory)

    @Query("SELECT * FROM login_history_table WHERE employeeId = :employeeId")
     fun getLoginHistoryByEmployeeId(employeeId: Int): LiveData<List<LoginHistory>>

}