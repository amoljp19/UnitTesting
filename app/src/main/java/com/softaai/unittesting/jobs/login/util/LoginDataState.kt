package com.softaai.unittesting.jobs.login.util

import com.softaai.unittesting.model.LoginUser
import kotlinx.coroutines.flow.Flow

sealed class LoginDataState {
    data class Error(val message: String?) : LoginDataState()
    object ValidCredentialsState : LoginDataState()
    object InValidEmailState : LoginDataState()
    object InValidPasswordState : LoginDataState()
    data class Success(val data: LoginUser?) : LoginDataState()
}