package com.dedeandres.covnews.data.dashboard.entity

import com.dedeandres.covnews.R

data class Hotline(
    val name: String,
    val phoneNumber: String,
    val logo: Int
)

fun getListHotline(): List<Hotline> = listOf(
    Hotline("Kementerian Kesehatan", "0215210411", R.drawable.ic_kementerian_kesehatan),
    Hotline("Kementerian Kesehatan", "081212123119", R.drawable.ic_kementerian_kesehatan),
    Hotline("Pemprov DKI Jakarta", "112", R.drawable.ic_dki),
    Hotline("Pemprov DKI Jakarta", "081388376955", R.drawable.ic_dki),
    Hotline("Pemprov Jawa Tengah", "0243580713", R.drawable.ic_jateng),
    Hotline("Pemprov Jawa Tengah", "082313600560", R.drawable.ic_jateng),
    Hotline("Pemprov Jawa Timur", "0318430313", R.drawable.ic_jatim),
    Hotline("Pemprov Jawa Timur", "081334367800", R.drawable.ic_jatim),
    Hotline("Pemprov Jawa Barat", "119", R.drawable.ic_jabar),
    Hotline("Pemprov Jawa Barat", "08112093306", R.drawable.ic_jabar),
    Hotline("Pemprov D.I Yogyakarta", "0274555585", R.drawable.ic_yogya),
    Hotline("Pemprov D.I Yogyakarta", "08112764800", R.drawable.ic_yogya)
)