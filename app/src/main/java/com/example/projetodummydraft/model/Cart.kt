package com.example.projetodummydraft.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart(
    val id: Int,
    val products: List<CartProduct>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val discountedTotal: Double,
    val userId: Int
): Parcelable