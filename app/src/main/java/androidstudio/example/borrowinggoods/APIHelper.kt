import android.content.Context
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiHelper {
    private const val BASE_URL = "https://webacp16.merak.web.id/API-peminjaman-barang/" // Ganti dengan URL API Anda

    // Fungsi untuk GET request
    fun getData(endpoint: String): String? {
        return try {
            val url = URL("$BASE_URL$endpoint")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

            val response = StringBuilder()
            BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
            }
            conn.disconnect()
            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Fungsi untuk POST request
    fun postData(endpoint: String, data: JSONObject): String? {
        return try {
            val url = URL("$BASE_URL$endpoint")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.doOutput = true

            OutputStreamWriter(conn.outputStream).use { writer ->
                writer.write(data.toString())
                writer.flush()
            }

            val response = StringBuilder()
            BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
            }
            conn.disconnect()
            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Fungsi untuk PUT request
    fun putData(endpoint: String, data: JSONObject): String? {
        return try {
            val url = URL("$BASE_URL$endpoint")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "PUT"
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.doOutput = true

            conn.outputStream.use { os ->
                os.write(data.toString().toByteArray(Charsets.UTF_8))
                os.flush()
            }

            val response = StringBuilder()
            BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
            }
            conn.disconnect()
            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Fungsi untuk DELETE request
    fun deleteData(endpoint: String): String? {
        return try {
            val url = URL("$BASE_URL$endpoint")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "DELETE"
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

            val response = StringBuilder()
            BufferedReader(InputStreamReader(conn.inputStream)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
            }
            conn.disconnect()
            response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Fungsi untuk menampilkan pesan singkat (Toast)
    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}
