package androidstudio.example.borrowinggoods

import ApiHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class admin_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val textview1 = findViewById<TextView>(R.id.textview1)
        val textView2 = findViewById<TextView>(R.id.textview2)
        val textView3 = findViewById<TextView>(R.id.textview3)
        val textView4 = findViewById<TextView>(R.id.textview4)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)

        val nama_admin = intent.getStringExtra("NAMA_ADMIN")
        val tanggal_lahir = intent.getStringExtra("TANGGAL_LAHIR")
        val username = intent.getStringExtra("USERNAME")
        val id_admin = intent.getIntExtra("ID_ADMIN", 0)

        textview1.text = intent.getStringExtra("NAMA_ADMIN")
        textView2.text = intent.getStringExtra("TANGGAL_LAHIR")
        textView3.text = intent.getStringExtra("USERNAME")
        textView4.text = intent.getStringExtra("ID_ADMIN")
        val id = intent.getIntExtra("ID_ADMIN" ,0)



        button1.setOnClickListener {
            delete(id)
        }
        button2.setOnClickListener {
            val intent = Intent(this, admin_edit::class.java).apply {
                putExtra("NAMA_ADMIN", nama_admin)
                putExtra("TANGGAL_LAHIR", tanggal_lahir)
                putExtra("USERNAME", username)

            }
        }


    }

    private fun delete(idAdmin: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val respone = ApiHelper.deleteData("DELETE_admin.php?id=$idAdmin")
            withContext(Dispatchers.Main){
                respone?.let {
                    if (respone != null){
                        ApiHelper.showMessage(this@admin_detail,"Data Sudah Di Hapus")

                    }else{
                        ApiHelper.showMessage(this@admin_detail, "Gagal Menghapus Data")
                    }
                } ?: run {
                    ApiHelper.showMessage(this@admin_detail, "Kesalahan jaringan Atau Server Tidak Merspone")
                }
            }
        }
    }
}