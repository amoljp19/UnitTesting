package com.softaai.unittesting.jobs.login.util

sealed class LoginDataState {
    data class Error(val message: String?) : LoginDataState()
    object ValidCredentialsState : LoginDataState()
    object InValidEmailState : LoginDataState()
    object InValidPasswordState : LoginDataState()
    object Success : LoginDataState()
}