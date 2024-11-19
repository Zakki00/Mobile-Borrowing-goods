package androidstudio.example.borrowinggoods

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ApiHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


//
//        button2.setOnClickListener {
//         val intent = Intent(this, Profile::class.java).apply {
//             flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//         }
//            startActivity(intent)
//            finish()
//        }
//        fetchData()
//

    }
    data class Data(
        val namaAdmin: String,
        val tanggalLahir: String,
        val username: String
    )
    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiHelper.getData("GET_admin.php")

            withContext(Dispatchers.Main) {
                response?.let {
                    val adminList = mutableListOf<Data>()

                    // Parsing JSON array response menjadi list AdminData
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val admin = Data(
                            item.getString("nama_admin"),
                            item.getString("tanggal_lahir"),
                            item.getString("username")
                        )
                        adminList.add(admin)
                    }
                    // Pasang adapter di RecyclerView
                    val recyclerView: RecyclerView = findViewById(R.id.Recycleview1)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = AdminAdapter(adminList)
                } ?: run {
                    Toast.makeText(this@MainActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Adapter AdminAdapter
    inner class AdminAdapter(private val adminList: List<Data>) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

        inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val namaAdmin: TextView = itemView.findViewById(R.id.textView1)
            val tanggalLahir: TextView = itemView.findViewById(R.id.textView1)
            val username: TextView = itemView.findViewById(R.id.textView2)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return AdminViewHolder(view)
        }

        override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
            val admin = adminList[position]
            holder.namaAdmin.text = admin.namaAdmin
            holder.tanggalLahir.text = admin.tanggalLahir
            holder.username.text = admin.username
        }

        override fun getItemCount(): Int = adminList.size
    }



    private fun fetchData(
        idTextView: TextView,
        namaTextView: TextView,
        jumlahTextView: TextView
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiHelper.getData("GET_barang.php")
            if (response != null && response.isNotEmpty()) {
                val jsonArray = JSONArray(response)

                // Mengambil data pertama dari array JSON
                val firstItem: JSONObject = jsonArray.getJSONObject(0)
                val idBarang = firstItem.getString("idbarang")
                val namaBarang = firstItem.getString("nama_barang")
                val jumlahBarang = firstItem.getString("jumlah_barang")

                withContext(Dispatchers.Main) {
                    // Menampilkan data pada TextView
                    idTextView.text = "ID Barang: $idBarang"
                    namaTextView.text = "Nama Barang: $namaBarang"
                    jumlahTextView.text = "Jumlah Barang: $jumlahBarang"
                }
            } else {
                withContext(Dispatchers.Main) {
                    // Menampilkan pesan jika data tidak tersedia
                    idTextView.text = "Tidak ada data ID"
                    namaTextView.text = "Tidak ada data Nama"
                    jumlahTextView.text = "Tidak ada data Jumlah"
                }
            }
        }
    }


}
