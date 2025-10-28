package uy.escuela.festival

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val items: List<Item>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = holder.itemView.context.getString(R.string.item_price, item.price)
        holder.itemQuantity.text = item.quantity.toString()

        holder.plusButton.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onQuantityChanged()
        }

        holder.minusButton.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                notifyItemChanged(position)
                onQuantityChanged()
            }
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        val plusButton: Button = itemView.findViewById(R.id.plusButton)
        val minusButton: Button = itemView.findViewById(R.id.minusButton)
    }
}