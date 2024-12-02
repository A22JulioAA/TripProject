package com.example.tripproject.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripproject.dao.RutaDAO
import com.example.tripproject.data.AppDatabase
import com.example.tripproject.data.DatabaseProvider
import com.example.tripproject.models.Ruta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RutaViewModel(application: Application) : AndroidViewModel(application) {
    private val rutaDao: RutaDAO
    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> get() = _rutas

    init {
        val db = DatabaseProvider.getDatabase(application)
        rutaDao = db.rutaDao()

        viewModelScope.launch {
            rutaDao.getAllRutas().collect { rutasList ->
                _rutas.value = rutasList
            }
        }
    }

    fun insertRuta(ruta: Ruta) {
        viewModelScope.launch {
            rutaDao.insert(ruta)
        }
    }
}