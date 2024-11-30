package com.caballero.leo.laboratoriocalificadosustitutorio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.caballero.leo.laboratoriocalificadosustitutorio.data.models.Post
import com.caballero.leo.laboratoriocalificadosustitutorio.data.network.RetrofitClient
import com.caballero.leo.laboratoriocalificadosustitutorio.databinding.ActivityEjercicio01Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ejercicio01 : AppCompatActivity() {

    private lateinit var binding: ActivityEjercicio01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Llamar a la API
        RetrofitClient.apiService.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body() ?: emptyList()
                    val adapter = PostAdapter(
                        posts,
                        onClick = { title ->
                            // Enviar título por SMS
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("smsto:+51925137361")
                                putExtra("sms_body", title)
                            }
                            startActivity(intent)
                        },
                        onLongClick = { body ->
                            // Enviar body por correo
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "message/rfc822"
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("victor.saico@tecsup.edu.pe"))
                                putExtra(Intent.EXTRA_SUBJECT, "Post Body")
                                putExtra(Intent.EXTRA_TEXT, body)
                            }
                            startActivity(intent)
                        }
                    )
                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@Ejercicio01, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@Ejercicio01, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
