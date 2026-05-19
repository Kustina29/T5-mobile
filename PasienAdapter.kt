package com.example.tugaspraktikum.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspraktikum.R
import com.example.tugaspraktikum.model.Pasien

class PasienAdapter(private val listPasien: List<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    inner class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvTanggalLahir: TextView = itemView.findViewById(R.id.tvTanggalLahir)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvJenisKelamin)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamat)
        val tvNoTelepon: TextView = itemView.findViewById(R.id.tvNoTelepon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pasien, parent, false)
        return PasienViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.tvNama.text = pasien.nama
        holder.tvTanggalLahir.text = "Tgl Lahir: ${pasien.tanggal_lahir}"
        val jk = if (pasien.jenis_kelamin == "L") "Laki-laki" else "Perempuan"
        holder.tvJenisKelamin.text = "Jenis Kelamin: $jk"
        holder.tvAlamat.text = "Alamat: ${pasien.alamat}"
        holder.tvNoTelepon.text = "No. Telepon: ${pasien.no_telepon}"
    }

    override fun getItemCount(): Int = listPasien.size
}