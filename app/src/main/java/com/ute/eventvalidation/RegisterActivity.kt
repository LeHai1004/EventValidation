package com.ute.eventvalidation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ute.eventvalidation.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private class TruongNhap(
        val ten: String,
        val khung: TextInputLayout,
        val kiemTra: (String) -> Int?
    )

    private val danhSachTruong by lazy {
        listOf(
            TruongNhap("HoTen", binding.tilHoTen, FormValidator::kiemTraHoTen),
            TruongNhap("Email", binding.tilEmail, FormValidator::kiemTraEmail),
            TruongNhap("Sdt", binding.tilSdt, FormValidator::kiemTraSdt),
            TruongNhap("Msv", binding.tilMsv, FormValidator::kiemTraMsv),
            TruongNhap("Lop", binding.tilLop, FormValidator::kiemTraLop)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate: man hinh Dang ki da khoi tao")

        guiSuKienChoCacO()
        binding.btnDangKi.setOnClickListener { xuLyDangKi() }
        binding.btnSangChiTiet.setOnClickListener { chuyenSangChiTiet() }
    }

    private fun guiSuKienChoCacO() {
        danhSachTruong.forEach { truong ->
            val oNhap = truong.khung.editText ?: return@forEach

            // Go phim lai thi xoa loi cu cua o do
            oNhap.doAfterTextChanged { truong.khung.error = null }

            // Roi khoi o nhap (da co chu) thi kiem tra luon
            oNhap.setOnFocusChangeListener { _, dangFocus ->
                if (!dangFocus && layChu(truong.khung).isNotEmpty()) {
                    Log.d(TAG, "roi o ${truong.ten}, kiem tra ngay")
                    kiemTraMotTruong(truong)
                }
            }
        }
    }

    private fun layChu(khung: TextInputLayout): String =
        khung.editText?.text?.toString().orEmpty().trim()

    private fun kiemTraMotTruong(truong: TruongNhap): Boolean {
        val loi = truong.kiemTra(layChu(truong.khung))
        truong.khung.error = loi?.let { getString(it) }
        Log.d(TAG, "kiemTra ${truong.ten}: ${if (loi == null) "OK" else getString(loi)}")
        return loi == null
    }

    private fun xuLyDangKi() {
        Log.d(TAG, "bam nut Dang ki, bat dau kiem tra 5 truong")

        // filterNot chay het ca 5 o nen o nao sai cung hien loi cung luc
        val truongSai = danhSachTruong.filterNot { kiemTraMotTruong(it) }

        if (truongSai.isNotEmpty()) {
            Log.w(TAG, "that bai: ${truongSai.size} o sai -> ${truongSai.joinToString { it.ten }}")
            truongSai.first().khung.editText?.requestFocus()
            return
        }

        Log.i(TAG, "thanh cong: ca 5 truong hop le")
        Snackbar.make(binding.root, R.string.msg_dang_ki_thanh_cong, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_xem_chi_tiet) { chuyenSangChiTiet() }
            .show()
    }

    private fun chuyenSangChiTiet() {
        Log.d(TAG, "intent: Register -> Detail")
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(ExtraKeys.HO_TEN, layChu(binding.tilHoTen))
            putExtra(ExtraKeys.EMAIL, layChu(binding.tilEmail))
            putExtra(ExtraKeys.SDT, layChu(binding.tilSdt))
            putExtra(ExtraKeys.MSV, layChu(binding.tilMsv))
            putExtra(ExtraKeys.LOP, layChu(binding.tilLop))
        }
        startActivity(intent)
    }

    private companion object {
        const val TAG = "REGISTER_LOG"
    }
}