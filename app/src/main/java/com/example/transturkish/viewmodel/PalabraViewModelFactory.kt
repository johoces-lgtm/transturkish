package com.example.transturkish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.transturkish.data.remote.RetrofitInstance
import com.example.transturkish.data.repository.PalabraRepository

class PalabraViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(PalabraViewModel::class.java)) {

            val apiService = RetrofitInstance.crearServicio(
                "http://10.0.2.2:8080/"
            )

            val repository = PalabraRepository(apiService)

            @Suppress("UNCHECKED_CAST")
            return PalabraViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}
