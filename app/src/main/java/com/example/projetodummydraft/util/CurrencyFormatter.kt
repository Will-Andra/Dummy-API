package com.example.projetodummydraft.util

import java.text.NumberFormat
import java.util.Locale

class CurrencyFormatter {

    fun formatToBRL(value: Double): String {
        //função de extensão (extension function)
        //adiciona um novo comportamento do tipo Double, sem modificar a classe Double original
        //o nome da função é "toBRL" e ao final retorna uma String
        //Resumo: “Crie uma função chamada toBRL que pertence ao Double e retorna uma String”
        val formatoBR = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatoBR.format(value)  //retorna o value formatado

    }
}