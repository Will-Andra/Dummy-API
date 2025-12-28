package com.example.projetodummydraft.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val address: Address,
    val age: Int,
    val birthDate: String,
    val bloodGroup: String,
    val ein: String,
    val email: String,
    val eyeColor: String,
    val firstName: String,
    val gender: String,
    val height: Double,
    val image: String,
    val ip: String,
    val lastName: String,
    val macAddress: String,
    val maidenName: String,
    val password: String,
    val phone: String,
    val role: String,
    val ssn: String,
    val university: String,
    val userAgent: String,
    val username: String,
    val company: Company,
    val weight: Double
): Parcelable