package androidstudio.example.borrowinggoods

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
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
import org.json.JSONObject

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val text1: TextView = findViewById(R.id.editTextTextPassword1)
        val text2: TextView = findViewById(R.id.editTextTextPassword2)
        val text3: TextView = findViewById(R.id.editTextTextPassword3)
        val text4: TextView = findViewById(R.id.editTextTextPassword4)
        val button1: Button = findViewById(R.id.button1)
        text2.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    // Format tanggal menjadi "YYYY-MM-dd"
                    val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                    text2.setText(formattedDate)
                }, year, month, day)

            datePickerDialog.show()
        }

        button1.setOnClickListener {
            val input = text3.text.toString().trim()
            if(input == "Admin"){
                val nama = text1.text.toString()
                val tanggalLahir = text2.text.toString()
                val username = text3.text.toString()
                val password = text4.text.toString()
                signUp_Admin(nama, tanggalLahir, username, password)
            }else{
                val nama = text1.text.toString()
                val tanggalLahir = text2.text.toString()
                val username = text3.text.toString()
                val password = text4.text.toString()
                SignUP_user(nama, tanggalLahir, username, password)
            }
        }
    }

    // kode untuk function yang lebih kompleks
    fun signUp_Admin(namaAdmin: String, tanggalLahir: String, username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {

            // Data yang akan dikirim
            val jsonData = JSONObject().apply {
                put("nama_admin", namaAdmin)
                put("tanggal_lahir", tanggalLahir)
                put("username", username)
                put("password", password)
            }
            val url = ApiHelper.postData("POST_admin.php", jsonData)


            withContext(Dispatchers.Main) {
                if (url != null) {
                    Toast.makeText(this@Signup, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                 val intent =   Intent(this@Signup, login::class.java).apply {
                     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                 }
                    startActivity(intent)
                    // Arahkan ke halaman login atau halaman lainnya
                } else {
                    Toast.makeText(this@Signup, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun SignUP_user(nama: String, tanggalLahir: String, username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonData = JSONObject().apply {
                put("nama_user", nama)
                put("tanggal_lahir", tanggalLahir)
                put("username", username)
                put("password", password)
            }
            val url = ApiHelper.postData("POST_user.php", jsonData)
            withContext(Dispatchers.Main) {
                if (url != null) {
                    Toast.makeText(this@Signup, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                    val intent =  Intent(this@Signup, login::class.java).apply {
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    startActivity(intent)

                    // Arahkan ke halaman login atau halaman lainnya
                }else{
                    Toast.makeText(this@Signup, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}