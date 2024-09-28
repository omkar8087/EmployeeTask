package com.example.employeetask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeetask.R
import com.example.employeetask.model.Employee

class EmployeeAdapter(
    private var employeeList: List<Employee>,

    private val onDeleteClick: (Employee) -> Unit // Add a callback to handle delete click
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {


    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val  employeeName: TextView = itemView.findViewById(R.id.tvEmployeeName)
        val employeeUsername : TextView = itemView.findViewById(R.id.tvEmployeeUsername)
        val deleteButton : Button = itemView.findViewById(R.id.tvDeleteEmployee)
        val historyContainer: ViewGroup = itemView.findViewById(R.id.historyContainer)

        fun bind(employee: Employee){
            employeeName.text = employee.name
            employeeUsername.text = employee.username

            historyContainer.removeAllViews()

            employee.history.forEach { historyEntry ->
                val historyView = LayoutInflater.from(itemView.context).inflate(R.layout.history_item_layout, historyContainer, false)

                val tvDate: TextView = historyView.findViewById(R.id.tvDate)
                val tvTime: TextView = historyView.findViewById(R.id.tvTime)
                val tvAddress: TextView = historyView.findViewById(R.id.tvAddress)

                tvDate.text = historyEntry.date
                tvTime.text = historyEntry.time
                tvAddress.text = historyEntry.address

                historyContainer.addView(historyView)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(employee)
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeAdapter.EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item_layout, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.EmployeeViewHolder, position: Int) {
        val currentEmployee = employeeList[position]
        holder.employeeName.text = currentEmployee.name
        holder.employeeUsername.text = currentEmployee.username

        //Clear previous history views
        holder.historyContainer.removeAllViews()
        holder.bind(currentEmployee)











        //set click listener for delete button
        holder.deleteButton.setOnClickListener {
            onDeleteClick(currentEmployee)
        }


    }

    override fun getItemCount(): Int {
        return employeeList.size
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setEmployees(employees: List<Employee>) {
        this.employeeList = employees
        notifyDataSetChanged()
    }
}