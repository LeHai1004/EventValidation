package com.ute.eventvalidation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ute.eventvalidation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: man hinh Chi tiet da khoi tao")

        hienThiThongTin()
        binding.btnVeDangKi.setOnClickListener { quayVeDangKi() }
    }

    private fun hienThiThongTin() {
        binding.tvHoTen.text = layExtra(ExtraKeys.HO_TEN)
        binding.tvEmail.text = layExtra(ExtraKeys.EMAIL)
        binding.tvSdt.text = layExtra(ExtraKeys.SDT)
        binding.tvMsv.text = layExtra(ExtraKeys.MSV)
        binding.tvLop.text = layExtra(ExtraKeys.LOP)
        Log.i(TAG, "da nhan du lieu: ${binding.tvHoTen.text} - ${binding.tvMsv.text}")
    }

    private fun layExtra(khoa: String): String =
        intent.getStringExtra(khoa).orEmpty().ifBlank { "—" }

    private fun quayVeDangKi() {
        Log.d(TAG, "intent: Detail -> Register")
        val intent = Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }

    private companion object {
        const val TAG = "DETAIL_LOG"
    }
}