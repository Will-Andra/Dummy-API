package com.example.projetodummydraft.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val id: Int,
    val price: Double,
    val discountPercentage: Double,
    val discountedTotal: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
): Parcelable