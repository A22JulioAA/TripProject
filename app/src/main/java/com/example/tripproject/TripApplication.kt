package com.example.tripproject

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            iniciarMonedasBase()
        }
    }

    private suspend fun iniciarMonedasBase () {
        val database = DatabaseProvider.getDatabase(this)
        val monedaDao = database.monedaDao()

        if (monedaDao.getMonedas().isEmpty()) {
            val monedasBase = listOf(
                Moneda(nombre = "Euro", codigo = "EUR", simbolo = "€"),
                Moneda(nombre = "Dólar Estadounidense", codigo = "USD", simbolo = "$"),
                Moneda(nombre = "Yen japonés", codigo = "JPY", simbolo = "¥"),
                Moneda(nombre = "Libra esterlina", codigo = "GBP", simbolo = "£"),
                Moneda(nombre = "Dólar Canadiense", codigo = "CAD", simbolo = "$"),
                Moneda(nombre = "Dólar australiano", codigo = "AUD", simbolo = "$"),
                Moneda(nombre = "Dólar neozelandés", codigo = "NZD", simbolo = "$"),
                Moneda(nombre = "Franco suízo", codigo = "CHF", simbolo = "CHF"),
                Moneda(nombre = "Yuan chino", codigo = "CNY", simbolo = "¥"),
                Moneda(nombre = "Rublo ruso", codigo = "RUB", simbolo = "₽"),
                Moneda(nombre = "Real brasileño", codigo = "BRL", simbolo = "R$"),
                Moneda(nombre = "Peso mexicano", codigo = "MXN", simbolo = "$"),
                Moneda(nombre = "Peso argentino", codigo = "ARS", simbolo = "$"),
            )
            monedaDao.insertMoneda(monedasBase)
        }
    }
}