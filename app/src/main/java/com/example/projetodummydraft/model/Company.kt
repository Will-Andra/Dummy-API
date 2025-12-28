package com.example.projetodummydraft.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val name: String,
    val title: String,
    val department: String
): Parcelable
