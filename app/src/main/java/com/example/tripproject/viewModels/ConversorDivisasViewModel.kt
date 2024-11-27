package com.example.tripproject.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripproject.dao.MonedaDAO
import com.example.tripproject.models.Moneda
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