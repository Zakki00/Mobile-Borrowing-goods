package androidstudio.example.borrowinggoods

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ApiHelper
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button2 = findViewById<Button>(R.id.button2)



        button2.setOnClickListener {
         val intent = Intent(this, Profile::class.java).apply {
             flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         }
            startActivity(intent)
        }
      getbarang()

    }
    data class data(
        val idBarang:Int,
        val namaBarang: String,
        val jumlahBarang: String
    )
    private fun getbarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiHelper.getData("GET_barang.php")

            withContext(Dispatchers.Main) {
                response?.let {
                    val list = mutableListOf<data>()

                    // Parsing JSON array response menjadi list AdminData
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        val barang  = data(
                            item.getInt("idbarang"),
                            item.getString("nama_barang"),
                            item.getString("jumlah_barang")

                        )
                        list.add(barang)
                    }
                    // Pasang adapter di RecyclerView
                    val recyclerView: RecyclerView = findViewById(R.id.Recycleview1)
                    recyclerView.layoutManager = LinearLayoutManager(this@User)
                    recyclerView.adapter = BarangAdapter(list) { barang ->
                        // Aksi ketika item diklik
                        val intent = Intent(this@User, Peminjaman::class.java).apply {
                           putExtra("ID_BARANG", barang.idBarang)
                            putExtra("NAMA_BARANG", barang.namaBarang)
                            putExtra("JUMLAH_BARANG", barang.jumlahBarang)
                        }
                        startActivity(intent)
                    }
                } ?: run {
                    Toast.makeText(this@User, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    inner class BarangAdapter(
        private val baranglist: List<data>,
        private val onItemClick: (data) -> Unit
    ) : RecyclerView.Adapter<BarangAdapter.Barangview>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Barangview {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return Barangview(view)
        }

        inner class Barangview(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val namaBarang: TextView = itemView.findViewById(R.id.textView1)
            val jumlahBarang: TextView = itemView.findViewById(R.id.textView2)

        }
        override fun onBindViewHolder(holder: Barangview, position: Int) {
            val barang = baranglist[position]
            holder.namaBarang.text = barang.namaBarang
            holder.jumlahBarang.text = barang.jumlahBarang


            // Set klik listener untuk item
            holder.itemView.setOnClickListener {
                onItemClick(barang)
            }
        }

        override fun getItemCount(): Int = baranglist.size
    }





    //
//
//    data class Data(
//        val idAdmin : Int,
//        val namaAdmin: String,
//        val tanggalLahir: String,
//        val username: String
//    )
//    private fun fetchData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = ApiHelper.getData("GET_barang.php")
//
//            withContext(Dispatchers.Main) {
//                response?.let {
//                    val adminList = mutableListOf<Data>()
//
//                    // Parsing JSON array response menjadi list AdminData
//                    val jsonArray = JSONArray(response)
//                    for (i in 0 until jsonArray.length()) {
//                        val item = jsonArray.getJSONObject(i)
//                        val admin = Data(
//                            item.getInt("idadmin"),
//                            item.getString("nama_admin"),
//                            item.getString("tanggal_lahir"),
//                            item.getString("username")
//                        )
//                        adminList.add(admin)
//                    }
//                    // Pasang adapter di RecyclerView
//                    val recyclerView: RecyclerView = findViewById(R.id.Recycleview1)
//                    recyclerView.layoutManager = LinearLayoutManager(this@User)
//                    recyclerView.adapter = AdminAdapter(adminList) { admin ->
//                        // Aksi ketika item diklik
//                        val intent = Intent(this@User, admin_detail::class.java).apply {
//                            putExtra("ID_ADMIN", admin.idAdmin)
//                            putExtra("NAMA_ADMIN", admin.namaAdmin)
//                            putExtra("TANGGAL_LAHIR", admin.tanggalLahir)
//                            putExtra("USERNAME", admin.username)
//                        }
//                        startActivity(intent)
//                    }
//                } ?: run {
//                    Toast.makeText(this@User, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//    }
//
//    // Adapter AdminAdapter
//    inner class AdminAdapter(
//        private val adminList: List<Data>,
//        private val onItemClick: (Data) -> Unit
//    ) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
//            return AdminViewHolder(view)
//        }
//
//        inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            val namaAdmin: TextView = itemView.findViewById(R.id.textView1)
//            val tanggalLahir: TextView = itemView.findViewById(R.id.textView2)
//            val username: TextView = itemView.findViewById(R.id.textView3)
//        }
//
//
//        override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
//            val admin = adminList[position]
//            holder.namaAdmin.text = admin.namaAdmin
//            holder.tanggalLahir.text = admin.tanggalLahir
//            holder.username.text = admin.username
//
//            // Set klik listener untuk item
//            holder.itemView.setOnClickListener {
//                onItemClick(admin)
//            }
//        }
//
//        override fun getItemCount(): Int = adminList.size
//    }
//

//
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
//

}
