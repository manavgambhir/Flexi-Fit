package com.example.flexifit.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.flexifit.data.models.MealModel
import com.example.flexifit.data.repos.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {
    private val repository = MealRepository()
    private val _breakfastResult = MutableStateFlow<MealModel?>(null)
    val breakfastResult: StateFlow<MealModel?> = _breakfastResult.asStateFlow()

    private val _lunchResult = MutableStateFlow<MealModel?>(null)
    val lunchResult: StateFlow<MealModel?> = _lunchResult.asStateFlow()

    private val _dinnerResult = MutableStateFlow<MealModel?>(null)
    val dinnerResult: StateFlow<MealModel?> = _dinnerResult.asStateFlow()

    fun getMealPlan(
        q: String,
        health: String,
        mealType: String,
        calories: String
    ){
        viewModelScope.launch {
            try{
                val response = repository.getMealPlan(q,health,mealType,calories)
                if(response.isSuccessful){
                    when(mealType.lowercase()){
                        "breakfast" -> _breakfastResult.value = response.body()
                        "lunch" -> _lunchResult.value = response.body()
                        "dinner" -> _dinnerResult.value = response.body()
                    }
                }
            } catch (e:Exception){
                Log.e("MealVMError", "Error fetching meal plan", e)
            }
        }
    }
}