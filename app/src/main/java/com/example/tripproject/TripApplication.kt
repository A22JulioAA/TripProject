package com.example.tripproject

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TripApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        iniciarMonedasBase()
    }

    private fun iniciarMonedasBase() {
        applicationScope.launch {
            try {
                populateDefaultCurrencies()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun populateDefaultCurrencies () {
        val database = DatabaseProvider.getDatabase(this)
        val monedaDao = database.monedaDao()

        val monedas = monedaDao.getMonedas().first()

        if (monedas.isEmpty()) {
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