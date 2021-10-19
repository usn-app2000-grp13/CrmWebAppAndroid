package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeAdapter
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel
import java.io.IOException


class EmployeeFragment : Fragment() {

    private val employeeList = mutableListOf<Employee>()

    lateinit var viewModel: EmployeeViewModel
    lateinit var binding: FragmentEmployeeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentEmployeeBinding.inflate(inflater)
        //val myDataset = getEmployeeList()
        //val adapter = EmployeeAdapter(requireContext(), myDataset)

        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        viewModel.getEmployees()

        val adapter = EmployeeAdapter(requireContext(), employeeList)

        viewModel.employee.observe(viewLifecycleOwner, {
            employeeList.clear()
            employeeList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        binding.empRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: EmployeeAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("employee", employeeList[position])
                findNavController().navigate(R.id.action_employeeFragment_to_employeeCard, bundle)
            }
        })
        binding.empRecyclerView.setHasFixedSize(true)

        // Inflate the layout for this fragment
        binding.fabNewEmployee.setOnClickListener {
            findNavController().navigate(R.id.action_employeeFragment_to_newEmployeeFragment2)
        }
        return binding.root
    }

    fun getEmployeeList(): List<Employee>{
        val jsonFileString = getJSONDataFromAsset(requireContext(), "employees.json")
        if(jsonFileString != null){
            Log.i("JSONENTRY", jsonFileString)
        }
        val gson = Gson()
        val listEmployeeType = object : TypeToken<List<Employee>>() {}.type

        var employees: List<Employee> = gson.fromJson(jsonFileString, listEmployeeType)
        employees.forEachIndexed { idx, employee -> Log.i("data", ">Item $idx: \n$employee") }
        return employees
    }

    fun getJSONDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}