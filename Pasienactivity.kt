package com.example.tugaspraktikum

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspraktikum.adapter.PasienAdapter
import com.example.tugaspraktikum.model.PasienResponse
import com.example.tugaspraktikum.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasienActivity : AppCompatActivity() {

    private lateinit var rvPasien: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvWelcome: TextView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasien)

        rvPasien = findViewById(R.id.rvPasien)
        progressBar = findViewById(R.id.progressBar)
        tvWelcome = findViewById(R.id.tvWelcome)
        tvError = findViewById(R.id.tvError)

        val token = intent.getStringExtra("TOKEN") ?: ""
        val nama = intent.getStringExtra("NAMA") ?: ""

        tvWelcome.text = "Selamat datang, $nama!"

        rvPasien.layoutManager = LinearLayoutManager(this)

        fetchDataPasien(token)
    }

    private fun fetchDataPasien(token: String) {
        progressBar.visibility = View.VISIBLE
        tvError.visibility = View.GONE

        RetrofitClient.instance.getPasien("Bearer $token")
            .enqueue(object : Callback<PasienResponse> {
                override fun onResponse(
                    call: Call<PasienResponse>,
                    response: Response<PasienResponse>
                ) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful && response.body()?.success == true) {
                        val listPasien = response.body()?.data ?: emptyList()
                        rvPasien.adapter = PasienAdapter(listPasien)
                    } else {
                        tvError.visibility = View.VISIBLE
                        tvError.text = "Gagal mengambil data pasien"
                    }
                }

                override fun onFailure(call: Call<PasienResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Error: ${t.message}"
                }
            })
    }
}