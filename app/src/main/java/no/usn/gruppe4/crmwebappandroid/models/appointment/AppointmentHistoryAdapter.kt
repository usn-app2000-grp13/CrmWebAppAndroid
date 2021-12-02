package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.Tools

class AppointmentHistoryAdapter(private val context: Context, private val dataset: List<Appointment>): RecyclerView.Adapter<AppointmentHistoryAdapter.ItemViewHolder>() {

    private val tools = Tools()
    private lateinit var mlistener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mlistener = listener
    }

    class ItemViewHolder(val view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val date = view.findViewById<TextView>(R.id.txDate)
        val time = view.findViewById<TextView>(R.id.txTime)
        val service = view.findViewById<TextView>(R.id.txService)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.appointment_history_item, parent, false)
        return ItemViewHolder(adapterLayout, mlistener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.date.text = tools.formatDate(item.date!!)
        holder.time.text = tools.timeIndexFormat(item.timeindex!!)
        if (item.services?.isNotEmpty() == true){
            holder.service.text = item.services?.get(0)?._service!!.name
        }else{
            holder.service.text = "null"
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}