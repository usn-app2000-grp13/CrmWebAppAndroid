import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.tj233512.myapplication.serviceAtribute

// Adapteren lager views til items i recyclerViev. Og å sette content for våre items
class ServiceAdapter(
    var todos: List<serviceAtribute>    // en liste med todo-okbjekter
) : RecyclerView.Adapter<ServiceAdapter.TodoViewHolder>() {

    // Hver adapter for recyclerviwet trenger en indre klasse som er en viewHolder klasse
    // Holder view'ene til recyclerViewet. Itemet vi definerte i item_todo.xml
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Denne metoden kalles når f eks brukerern scroller og et nytt item må vises
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return TodoViewHolder(view)
    }
    // Binder data til våre items
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {        // itemView holder alle viewene i itemdoTo
            val tvNavn : TextView = findViewById(R.id.tvNavn)
            tvNavn.text = todos[position].navn
            val tvVerdi : TextView = findViewById(R.id.tvVerdi)
            tvVerdi.text = todos[position].verdi
        }
    }
    //returnerer hvor mange items vi har i recyclerview
    override fun getItemCount(): Int {
        return todos.size
    }
}
