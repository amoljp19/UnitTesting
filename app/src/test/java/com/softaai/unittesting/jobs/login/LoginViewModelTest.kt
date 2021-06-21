package com.softaai.unittesting.jobs.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.unittesting.data.remote.Resource
import com.softaai.unittesting.data.repository.LoginUserRepository
import com.softaai.unittesting.jobs.CoroutineTestRule
import com.softaai.unittesting.jobs.login.util.LoginDataState
import com.softaai.unittesting.jobs.login.util.LoginValidator
import com.softaai.unittesting.model.JobsItemApiResponse
import com.softaai.unittesting.model.LoginUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

//@RunWith(PowerMockRunner::class)
@RunWith(JUnit4::class)
@PrepareForTest(LoginValidator::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var loginUserRepository: LoginUserRepository

    @Mock
    private lateinit var loginUser: LoginUser

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


    @Test
    fun testIfPasswordInvalid_ShowPasswordError() {
        //Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { true }
        `when`(LoginValidator.isPasswordValid(anyString())).thenAnswer { false }

        //Act
        loginViewModel.doLogin(EMAIL, PASSWORD)

        //Assert
        verify(mockObserverForStates).onChanged(LoginDataState.InValidPasswordState)
        verifyNoMoreInteractions(mockObserverForStates)
    }


    @Test
    fun testIfEmailAndPasswordValid_DoLogin() {
         // Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { true }
        `when`(LoginValidator.isPasswordValid(anyString())).thenAnswer { true }

        val data = mock<LoginUser>()

        runBlockingTest {
            whenever(loginUserRepository.getLoginUserByCredentials(anyString(), anyString())) doReturn flowOf(
                data
            )
        }

        //Act
        loginViewModel.doLogin(EMAIL, PASSWORD)

        //Assert
        verify(mockObserverForStates).onChanged(LoginDataState.ValidCredentialsState)
        verify(
            mockObserverForStates, times(2)
        ).onChanged(LoginDataState.Success(ArgumentMatchers.any()))
        verifyNoMoreInteractions(mockObserverForStates)
    }

    @Test
    fun testThrowError_OnLoginFailed() {
        //Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { true }
        `when`(LoginValidator.isPasswordValid(anyString())).thenAnswer { true }

        // here we can assume any kind of exception, due to that login fails
        val error = RuntimeException()

        runBlocking {
            `when`(loginUserRepository.getLoginUserByCredentials(anyString(), anyString())).thenThrow(
                error
            )
        }

        //Act
        loginViewModel.doLogin(EMAIL, PASSWORD)


        //Assert
        verify(mockObserverForStates).onChanged(LoginDataState.ValidCredentialsState)
        verify(mockObserverForStates, times(2))
            .onChanged(LoginDataState.Error(ArgumentMatchers.any()))
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





