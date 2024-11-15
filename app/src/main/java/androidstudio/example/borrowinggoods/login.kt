package androidstudio.example.borrowinggoods
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Jika sudah login, langsung ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Mengakhiri activity login
            return
        }
        val buttonsignup: Button = findViewById(R.id.button1)
        val buttonlogin: Button = findViewById(R.id.button2)
        val username: EditText = findViewById(R.id.editTextText)
        val password: EditText = findViewById(R.id.editTextTextPassword1)

        buttonlogin.setOnClickListener {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()
            loginUser(usernameText, passwordText)
        }
        buttonsignup.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }


    }

    private fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiHelper.getData("GET_login_admin.php?username=$username&password=$password")

            withContext(Dispatchers.Main) {
                response?.let {
                    if (response.isNotEmpty()) {
                        val jsonArray = JSONArray(response)
                        if (jsonArray.length() > 0) {
                            val firstItem = jsonArray.getJSONObject(0)
                            val idAdmin = firstItem.optString("idadmin")
                            val namaAdmin = firstItem.optString("nama_admin")
                            val tanggalLahir = firstItem.optString("tanggal_lahir")
                            val username = firstItem.optString("username")
                            val password = firstItem.optString("password")

                            // Menyimpan data login ke SharedPreferences
                            val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putBoolean("isLoggedIn", true)
                                putString("idadmin", idAdmin)
                                putString("nama_admin", namaAdmin)
                                putString("tanggal_lahir", tanggalLahir)
                                putString("username", username)
                                putString("password", password)
                                apply()
                            }

                            // Pindah ke MainActivity setelah login sukses
                            Toast.makeText(this@login, "Login sukses", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@login, MainActivity::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(this@login, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(this@login, "Kesalahan jaringan atau server tidak merespons", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




}