package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.appointment_item.view.*
import no.usn.gruppe4.crmwebappandroid.R
import java.text.SimpleDateFormat
import java.util.*

class AppointmentAdapter(private val context: Context, private val dataset: List<Appointment>): RecyclerView.Adapter<AppointmentAdapter.ItemViewHolder>() {

    private lateinit var mlistener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onDeleteClick(position: Int)
        fun onEditClick(position: Int)
        fun onMessageClick(position: Int)
        fun onExpCusClick(position: Int);
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mlistener = listener
    }

    class ItemViewHolder(val view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val textViewTime: TextView = view.findViewById(R.id.calItemTime)
        val textViewCust: TextView = view.findViewById(R.id.calItemCustomer)
        val textViewServ: TextView = view.findViewById(R.id.calItemService)
        //expanded view
        val textViewCusExpand: EditText = view.findViewById(R.id.txtCustomer)
        val textViewEmpExpand: EditText = view.findViewById(R.id.txtEmployee)
        val textViewSerExpand: EditText = view.findViewById(R.id.txtService)
        val textViewNotExpand: EditText = view.findViewById(R.id.txtNotes)
        val textViewDateExpand: EditText = view.findViewById(R.id.txtDate)
        val textViewTimeExpand: EditText = view.findViewById(R.id.txtTime)
        //buttons
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnMessage: Button = view.findViewById(R.id.btnMessage)
        //listExpanders
        val btnTime: TextInputLayout = view.findViewById(R.id.tfTime)
        val btnDate: TextInputLayout = view.findViewById(R.id.tfDate)
        val btnExpCus: TextInputLayout = view.findViewById(R.id.neCustomer)
        val btnExpEmp: TextInputLayout = view.findViewById(R.id.neEmployee)
        val btnExpSer: TextInputLayout = view.findViewById(R.id.neService)
        //expanding
        val expandMore = view.findViewById<ConstraintLayout>(R.id.expanded_view)
        val expandLess = view.findViewById<ConstraintLayout>(R.id.minimized_View)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            btnDelete.setOnClickListener {
                listener.onDeleteClick(adapterPosition)
            }
            btnEdit.setOnClickListener {
                listener.onEditClick(adapterPosition)
            }
            btnMessage.setOnClickListener {
                listener.onMessageClick(adapterPosition)
            }
            btnExpSer.setOnClickListener {
                listener.onExpCusClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //create the new card
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return ItemViewHolder(adapterLayout, mlistener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val customerName = "${item.customers?.get(0)?._customer?.firstname} ${item.customers?.get(0)?._customer?.lastname}"
        val employeeName = "${item.employees?.get(0)?._employee?.firstname} ${item.employees?.get(0)?._employee?.lastname}"
        holder.textViewTime.text = timeIndexFormat(item.timeindex!!)
        holder.textViewCust.text = customerName
        holder.textViewCusExpand.setText(customerName)
        holder.textViewEmpExpand.setText(employeeName)
        holder.textViewSerExpand.setText("${item.services?.get(0)?._service?.name}")
        holder.textViewNotExpand.setText("${item.comment.toString()}")
        holder.textViewTimeExpand.setText(timeIndexFormat(item.timeindex!!))
        holder.textViewDateExpand.setText("${item.date?.dateToString("E dd MMM")}")
        holder.textViewServ.text = item.services?.get(0)?._service!!.name
        holder.btnExpEmp.setEndIconOnClickListener {
            // TODO: 16.11.2021 add expand employee view 
        }
        holder.btnExpSer.setEndIconOnClickListener {
            // TODO: 16.11.2021 add expand services view 
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * function for returning a time in hh:mm format from a timeindex
     */
    fun timeIndexFormat(timeindex: Int): String{
        var res = ""
        val clockM = timeindex % 60
        val clockH = (timeindex - clockM) / 60

        if (clockH < 10){
            res += "0$clockH:"
        }else{
            res += "$clockH:"
        }
        if (clockM < 10) {
            res += "0$clockM"
        }else{
            res += "$clockM"
        }
        return res
    }
    fun expandView(view: ItemViewHolder){
        if (view.expandLess.visibility == View.VISIBLE){
            view.expandLess.visibility = View.GONE
            view.expandMore.visibility = View.VISIBLE
        }else{
            view.expandLess.visibility = View.VISIBLE
            view.expandMore.visibility = View.GONE
        }
    }

    private fun Date.dateToString(format: String): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        //return the formatted date string
        return dateFormatter.format(this)
    }

}