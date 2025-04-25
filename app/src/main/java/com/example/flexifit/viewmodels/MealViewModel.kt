package com.example.flexifit.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.flexifit.data.models.MealModel
import com.example.flexifit.data.repos.MealRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MealViewModel : ViewModel() {
    private val repository = MealRepository()

    private val _mealPlanResult = MutableLiveData<Response<MealModel>>()
    val mealPlanResult: LiveData<Response<MealModel>> = _mealPlanResult

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
                    _mealPlanResult.postValue(response)
                }
            } catch (e:Exception){
                Log.e("MealViewModel", "Error fetching meal plan", e)
            }
        }
    }
}