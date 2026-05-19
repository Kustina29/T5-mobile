package com.example.tugaspraktikum

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugaspraktikum.model.LoginRequest
import com.example.tugaspraktikum.model.LoginResponse
import com.example.tugaspraktikum.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validasi input kosong
            if (email.isEmpty()) {
                etEmail.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            doLogin(email, password)
        }
    }

    private fun doLogin(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        btnLogin.isEnabled = false

        val request = LoginRequest(email, password)
        RetrofitClient.instance.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressBar.visibility = View.GONE
                btnLogin.isEnabled = true

                if (response.isSuccessful && response.body()?.success == true) {
                    val token = response.body()?.data?.token ?: ""
                    val nama = response.body()?.data?.user?.name ?: ""

                    // Pindah ke PasienActivity dan kirim token + nama
                    val intent = Intent(this@LoginActivity, PasienActivity::class.java)
                    intent.putExtra("TOKEN", token)
                    intent.putExtra("NAMA", nama)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login gagal: Email atau password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                btnLogin.isEnabled = true
                Toast.makeText(
                    this@LoginActivity,
                    "Gagal terhubung ke server: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}