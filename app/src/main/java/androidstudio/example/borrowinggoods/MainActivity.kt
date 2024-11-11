package androidstudio.example.borrowinggoods

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ApiHelper
import android.content.Context
import android.content.Intent
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
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val idAdmin = sharedPref.getString("idadmin", "ID Admin tidak ditemukan")
        val namaAdmin = sharedPref.getString("nama_admin", "Nama Admin tidak ditemukan")
        val username = sharedPref.getString("username", "Username tidak ditemukan")
        val password = sharedPref.getString("password", "Password tidak ditemukan")


        val textView1: TextView = findViewById(R.id.textView1)
        val taxtview2: TextView = findViewById(R.id.textView2)
        val textView3: TextView = findViewById(R.id.textView3)
        val textView4: TextView = findViewById(R.id.textView4)
        val button1: TextView = findViewById(R.id.button1)

        textView1.text = idAdmin
        taxtview2.text = namaAdmin
        textView3.text = username
        textView4.text = password
        button1.setOnClickListener {
            logout()
        }


        // Menampilkan data di TextView
//        findViewById<TextView>(R.id.textView1).text = idAdmin
//        findViewById<TextView>(R.id.textView2).text = namaAdmin
//        findViewById<TextView>(R.id.textView3).text = username

    }
    private fun logout() {
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", false)
            putString("idadmin", null)
            putString("nama_admin", null)
            putString("username", null)
            apply()
        }
        val intent = Intent(this, login::class.java)
        startActivity(intent)
        finish()
    }

//    private fun fetchData(
//        idTextView: TextView,
//        namaTextView: TextView,
//        jumlahTextView: TextView
//    ) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = ApiHelper.getData("GET_barang.php")
//            if (response != null && response.isNotEmpty()) {
//                val jsonArray = JSONArray(response)
//
//                // Mengambil data pertama dari array JSON
//                val firstItem: JSONObject = jsonArray.getJSONObject(0)
//                val idBarang = firstItem.getString("idbarang")
//                val namaBarang = firstItem.getString("nama_barang")
//                val jumlahBarang = firstItem.getString("jumlah_barang")
//
//                withContext(Dispatchers.Main) {
//                    // Menampilkan data pada TextView
//                    idTextView.text = "ID Barang: $idBarang"
//                    namaTextView.text = "Nama Barang: $namaBarang"
//                    jumlahTextView.text = "Jumlah Barang: $jumlahBarang"
//                }
//            } else {
//                withContext(Dispatchers.Main) {
//                    // Menampilkan pesan jika data tidak tersedia
//                    idTextView.text = "Tidak ada data ID"
//                    namaTextView.text = "Tidak ada data Nama"
//                    jumlahTextView.text = "Tidak ada data Jumlah"
//                }
//            }
//        }
//    }


}
