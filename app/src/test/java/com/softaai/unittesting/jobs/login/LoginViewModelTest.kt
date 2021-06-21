package com.softaai.unittesting.jobs.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.unittesting.data.repository.LoginUserRepository
import com.softaai.unittesting.jobs.CoroutineTestRule
import com.softaai.unittesting.jobs.login.util.LoginDataState
import com.softaai.unittesting.jobs.login.util.LoginValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

//@RunWith(PowerMockRunner::class)
@RunWith(JUnit4::class)
@PrepareForTest(LoginValidator::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var loginUserRepository: LoginUserRepository


    private val mockObserverForStates = mock<Observer<LoginDataState>>()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        mockStatic(LoginValidator::class.java)

        loginViewModel = LoginViewModel(loginUserRepository)

        loginViewModel.loginStateLiveData.observeForever(mockObserverForStates)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIfEmailInvalid_ShowEmailError() {

        //Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { false }

        //Act
        loginViewModel.doLogin(EMAIL, PASSWORD)

        //Assert
        verify(mockObserverForStates).onChanged(LoginDataState.InValidEmailState)
        verifyNoMoreInteractions(mockObserverForStates)

    }

    @After
    @Throws(Exception::class)
    fun tearDownClass() {
        loginViewModel.loginStateLiveData.removeObserver(mockObserverForStates)
    }

    companion object {
        private const val EMAIL = "amol@gmail.com"
        private const val PASSWORD = "123456"
    }

}





