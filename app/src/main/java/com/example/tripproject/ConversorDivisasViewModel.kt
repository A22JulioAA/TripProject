package com.example.tripproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ConversorDivisasViewModel (private val monedaDao: MonedaDAO) : ViewModel() {
    private val _monedas = MutableStateFlow<List<Moneda>>(emptyList())
    val monedas: StateFlow<List<Moneda>> get() = _monedas

    init {
        viewModelScope.launch {
            monedaDao.getMonedas().collectLatest {
                _monedas.value = it
            }
        }
    }
}