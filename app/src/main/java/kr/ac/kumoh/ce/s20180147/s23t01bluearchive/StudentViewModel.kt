package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StudentViewModel: ViewModel() {
    private val SERVER_URL = "https://port-0-s23w10backend-gj8u2llomit2u9.sel5.cloudtype.app/"
    private val studentApi: StudentApi
    private val _studentList = MutableLiveData<List<Student>>()
    private val _academyList = MutableLiveData<List<Academy>>()

    val studentList: LiveData<List<Student>>
        get() = _studentList

    val academyList: LiveData<List<Academy>>
        get() = _academyList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        studentApi = retrofit.create(StudentApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val studentResponse = studentApi.getStudent()
                _studentList.value = studentResponse
                val academyResponse = studentApi.getAcademy()
                _academyList.value = academyResponse
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}