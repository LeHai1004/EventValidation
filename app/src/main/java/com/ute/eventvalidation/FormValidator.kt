package com.ute.eventvalidation

import android.util.Patterns
import androidx.annotation.StringRes

object FormValidator {

    private val REGEX_CHU_CAI = Regex("^[\\p{L} ]+$")
    private val REGEX_SDT = Regex("^(03|05|07|08|09)\\d{8}$")
    private val REGEX_MSV = Regex("^\\d{8,12}$")
    private val REGEX_LOP = Regex("^[A-Za-z0-9._-]{3,20}$")

    // Trả về id chuỗi lỗi, hoặc null nếu dữ liệu hợp lệ
    @StringRes
    fun kiemTraHoTen(raw: String): Int? {
        val ten = raw.trim()
        return when {
            ten.isEmpty() -> R.string.err_ho_ten_rong
            !REGEX_CHU_CAI.matches(ten) -> R.string.err_ho_ten_ky_tu
            ten.split(Regex("\\s+")).size < 2 -> R.string.err_ho_ten_thieu_tu
            else -> null
        }
    }

    @StringRes
    fun kiemTraEmail(raw: String): Int? {
        val email = raw.trim()
        return when {
            email.isEmpty() -> R.string.err_email_rong
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.err_email_sai
            else -> null
        }
    }

    @StringRes
    fun kiemTraSdt(raw: String): Int? {
        val sdt = raw.trim()
        return when {
            sdt.isEmpty() -> R.string.err_sdt_rong
            !REGEX_SDT.matches(sdt) -> R.string.err_sdt_sai
            else -> null
        }
    }

    @StringRes
    fun kiemTraMsv(raw: String): Int? {
        val msv = raw.trim()
        return when {
            msv.isEmpty() -> R.string.err_msv_rong
            !REGEX_MSV.matches(msv) -> R.string.err_msv_sai
            else -> null
        }
    }

    @StringRes
    fun kiemTraLop(raw: String): Int? {
        val lop = raw.trim()
        return when {
            lop.isEmpty() -> R.string.err_lop_rong
            !REGEX_LOP.matches(lop) -> R.string.err_lop_sai
            else -> null
        }
    }
}