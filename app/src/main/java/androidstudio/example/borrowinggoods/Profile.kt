package androidstudio.example.borrowinggoods

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val nama = sharedPref.getString("nama_admin", "Nama Admin tidak ditemukan")
        val tanggal_lahir = sharedPref.getString("tanggal_lahir", "Username tidak ditemukan")
        val username = sharedPref.getString("username", "Username tidak ditemukan")

        findViewById<TextView>(R.id.textView1).text = "Selamat Datang " + nama
        findViewById<TextView>(R.id.textView1).text = username
          findViewById<TextView>(R.id.textView2).text = tanggal_lahir
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)

        button2.setOnClickListener {
            logut()
        }
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
    private fun logut(){
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putBoolean("isLoggedIn", false)
            putString("idadmin", null)
            putString("nama_admin", null)
            putString("username", null)
            apply()
        }
        val intent = Intent(this, login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        startActivity(intent)
        finish()
    }
}