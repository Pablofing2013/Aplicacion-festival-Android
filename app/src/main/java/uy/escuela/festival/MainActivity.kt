package uy.escuela.festival

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var totalPriceTextView: TextView
    private lateinit var receivedMoneyEditText: EditText
    private lateinit var calculateChangeButton: Button
    private lateinit var resetButton: Button
    private lateinit var changeTextView: TextView
    private lateinit var addProductButton: FloatingActionButton

    private val items = mutableListOf(
        Item("Juego", 10.0),
        Item("Margarita", 5.0),
        Item("Comida", 15.0),
        Item("Bebida", 2.0)
    )

    private val addProductLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val name = it.data?.getStringExtra("name")
            val price = it.data?.getDoubleExtra("price", 0.0)

            if (name != null && price != null) {
                items.add(Item(name, price))
                itemsRecyclerView.adapter?.notifyItemInserted(items.size - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRecyclerView = findViewById(R.id.itemsRecyclerView)
        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        receivedMoneyEditText = findViewById(R.id.receivedMoneyEditText)
        calculateChangeButton = findViewById(R.id.calculateChangeButton)
        resetButton = findViewById(R.id.resetButton)
        changeTextView = findViewById(R.id.changeTextView)
        addProductButton = findViewById(R.id.addProductButton)

        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsRecyclerView.adapter = ItemAdapter(items) { updateTotal() }

        calculateChangeButton.setOnClickListener {
            calculateChange()
        }

        resetButton.setOnClickListener {
            reset()
        }

        addProductButton.setOnClickListener {
            addProductLauncher.launch(Intent(this, AddProductActivity::class.java))
        }
    }

    private fun updateTotal() {
        val total = items.sumOf { it.price * it.quantity }
        totalPriceTextView.text = getString(R.string.total_price, total)
    }

    private fun calculateChange() {
        val total = items.sumOf { it.price * it.quantity }
        val received = receivedMoneyEditText.text.toString().toDoubleOrNull() ?: 0.0
        val change = received - total
        changeTextView.text = getString(R.string.change, change)
    }

    private fun reset() {
        items.forEach { it.quantity = 0 }
        itemsRecyclerView.adapter?.notifyDataSetChanged()
        updateTotal()
        receivedMoneyEditText.text.clear()
        changeTextView.text = getString(R.string.default_change)
    }
}