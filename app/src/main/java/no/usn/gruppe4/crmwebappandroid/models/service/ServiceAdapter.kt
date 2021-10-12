package no.usn.gruppe4.crmwebappandroid.models.employee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.service.Service

// Denne klassen brukes i servicefragment for å vise listen med services og håndterer klikk på services
class ServiceAdapter (private val context: Context, private val dataset: List<Service>): RecyclerView.Adapter<ServiceAdapter.ItemViewHolder>(){
    private lateinit var mlistener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mlistener = listener
    }

    // ***** Intern klasse. Det lages en av denne for hver service i listen. *****
    class ItemViewHolder(val view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        // Lager variabler som peker på textviews i item_service
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDuration: TextView = view.findViewById(R.id.tvDuration)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        // Hver service item i listen hav hver sin click listener
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // 1) Setter adapteren sin layout. (item_service)
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): ItemViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent,false)
        return ItemViewHolder(adapterLayout, mlistener)
    }

    // 2) Gir alle textviews i et item verdier ut i fra verdiene i service-listen
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.tvTitle.text = item.name
        holder.tvDuration.text = item.duration.toString()
        holder.tvPrice.text = item.price
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}