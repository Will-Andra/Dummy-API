package com.example.projetodummydraft.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseProducts(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
): Parcelable
@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String
): Parcelable