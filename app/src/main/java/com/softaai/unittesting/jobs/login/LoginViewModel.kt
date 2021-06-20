package com.softaai.unittesting.jobs.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softaai.unittesting.jobs.login.util.LoginDataState
import com.softaai.unittesting.jobs.login.util.LoginValidator

class LoginViewModel : ViewModel() {

    val loginStateLiveData = MutableLiveData<LoginDataState>()


    fun doLogin(userEmail: String, password: String) {
        if (areUserCredentialsValid(userEmail, password)) {
           //Todo on true go to main activity, on false show error
        }
    }


    private fun areUserCredentialsValid(userEmail: String, password: String): Boolean {
        return if (!LoginValidator.isEmailValid(userEmail)) {
            loginStateLiveData.postValue(LoginDataState.InValidEmailState)
            false
        } else if (!LoginValidator.isPasswordValid(password)) {
            loginStateLiveData.postValue(LoginDataState.InValidPasswordState)
            false
        } else {
            loginStateLiveData.postValue(LoginDataState.ValidCredentialsState)
            true
        }
    }

}