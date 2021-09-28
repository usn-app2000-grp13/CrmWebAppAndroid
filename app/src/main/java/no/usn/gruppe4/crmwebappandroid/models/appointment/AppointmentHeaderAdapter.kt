package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R

class AppointmentHeaderAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val curDate = "28/09/2021"
    val curEmployee = "Cato"

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
}