package uy.escuela.festival

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddProductActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        nameEditText = findViewById(R.id.nameEditText)
        priceEditText = findViewById(R.id.priceEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val price = priceEditText.text.toString().toDoubleOrNull()

            if (name.isNotEmpty() && price != null) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("price", price)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}