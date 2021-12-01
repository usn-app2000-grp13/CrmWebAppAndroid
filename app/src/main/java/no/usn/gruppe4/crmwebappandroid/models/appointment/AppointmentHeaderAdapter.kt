package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R
import java.util.*

class AppointmentHeaderAdapter(date: Date, curUser: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    @RequiresApi(Build.VERSION_CODES.N)
    var curDate = setCurDate(date)
    val curEmployee = curUser

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val dateTextView: TextView = itemView.findViewById(R.id.apHeader)
        val employeeTextView: TextView = itemView.findViewById(R.id.apEmployee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.apHeader).text = curDate
        holder.itemView.findViewById<TextView>(R.id.apEmployee).text = curEmployee
    }

    override fun getItemCount(): Int {
        return 1
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setCurDate(newDate: Date) : String{
        val formater = SimpleDateFormat("dd/MM/yyyy")
        return  formater.format(newDate)
    }
}