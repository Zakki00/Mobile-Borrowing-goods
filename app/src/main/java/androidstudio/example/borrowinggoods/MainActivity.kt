package androidstudio.example.borrowinggoods

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ApiHelper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text: TextView = findViewById(R.id.textView1)
        val text2: TextView = findViewById(R.id.textView2)

        fetchData(text)



    }

    private fun fetchData(text: TextView) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiHelper.getData("GET_barang.php")
            if (response != null && response.isNotEmpty()) {
                val jsonArray = JSONArray(response)
                val stringBuilder = StringBuilder()

                for (i in 0 until jsonArray.length()) {
                    val item: JSONObject = jsonArray.getJSONObject(i)
                    val idBarang = item.getString("idbarang")
                    val namaBarang = item.getString("nama_barang")
                    val jumlahBarang = item.getString("jumlah_barang")

                    stringBuilder.append("ID Barang: $idBarang\n")
                    stringBuilder.append("Nama Barang: $namaBarang\n")
                    stringBuilder.append("Jumlah Barang: $jumlahBarang\n\n")
                }

                withContext(Dispatchers.Main) {
                    text.text = stringBuilder.toString()
                }
            } else {
                withContext(Dispatchers.Main) {
                    text.text = "Tidak ada data yang tersedia"
                }
            }
        }

    }


}