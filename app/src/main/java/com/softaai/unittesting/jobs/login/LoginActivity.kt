package com.softaai.unittesting.jobs.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.softaai.unittesting.databinding.ActivityLoginBinding
import com.softaai.unittesting.jobs.login.util.LoginDataState
import com.softaai.unittesting.jobs.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val mViewModel: LoginViewModel by viewModels()

    lateinit var mViewBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        mViewModel.saveLoginUser()

        observeLoginStates()

        mViewBinding.login.setOnClickListener {
            hideKeyboard(mViewBinding.login)

            mViewModel.doLogin(
                mViewBinding.inputUsername.text.toString(),
                mViewBinding.inputPassword.text.toString()
            )
        }
    }


    private fun observeLoginStates() {
        mViewModel.loginStateLiveData.observe(this) { dataState ->
            when (dataState) {
                is LoginDataState.Success -> {
                    mViewBinding.loading.visibility = View.GONE
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    showMainScreen()
                }
                is LoginDataState.Error -> {
                    mViewBinding.loading.visibility = View.GONE
                    Toast.makeText(this, dataState.message, Toast.LENGTH_SHORT).show()
                }
                is LoginDataState.ValidCredentialsState -> {
                    mViewBinding.loading.visibility = View.GONE
                    resetEmailError()
                    resetPasswordError()
                }
                is LoginDataState.InValidEmailState -> {
                    setEmailError()

                }
                is LoginDataState.InValidPasswordState -> {
                    resetEmailError()
                    setPasswordError()
                }
            }
        }
    }

    private fun setEmailError() {
        mViewBinding.textInputUsername.error = "Please enter a valid email ID"
        mViewBinding.textInputUsername.isErrorEnabled = true
    }

    private fun setPasswordError() {
        mViewBinding.textInputPassword.error = "Please enter a valid password"
        mViewBinding.textInputPassword.isErrorEnabled = true
    }

    private fun resetEmailError() {
        mViewBinding.textInputUsername.error = null
        mViewBinding.textInputUsername.isErrorEnabled = false
    }

    private fun resetPasswordError() {
        mViewBinding.textInputPassword.error = null
        mViewBinding.textInputPassword.isErrorEnabled = false
    }

    private fun showMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
