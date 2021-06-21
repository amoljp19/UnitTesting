package com.softaai.unittesting.jobs.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.unittesting.data.repository.LoginUserRepository
import com.softaai.unittesting.jobs.CoroutineTestRule
import com.softaai.unittesting.jobs.login.util.LoginDataState
import com.softaai.unittesting.jobs.login.util.LoginValidator
import com.softaai.unittesting.model.LoginUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.MockedStatic
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.powermock.core.classloader.annotations.PrepareForTest

//@RunWith(PowerMockRunner::class)
@RunWith(JUnit4::class)
@PrepareForTest(LoginValidator::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var loginUserRepository: LoginUserRepository

    private lateinit var loginValidator: MockedStatic<LoginValidator>

    private val mockObserverForStates = mock<Observer<LoginDataState>>()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        loginValidator = mockStatic(LoginValidator::class.java)

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

    @ExperimentalCoroutinesApi
    @Test
    fun testIfEmailAndPasswordValid_DoLogin() {
        // Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { true }
        `when`(LoginValidator.isPasswordValid(anyString())).thenAnswer { true }

        val data = mock<LoginUser>()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(
                loginUserRepository.getLoginUserByCredentials(
                    anyString(),
                    anyString()
                )
            ) doReturn flowOf(
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

    @ExperimentalCoroutinesApi
    @Test
    fun testIfEmailAndPasswordValid_ButDoLogin_ForInvalidLoginUser() {
        // Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { true }
        `when`(LoginValidator.isPasswordValid(anyString())).thenAnswer { true }

        //val data = mock<LoginUser>()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(
                loginUserRepository.getLoginUserByCredentials(
                    anyString(),
                    anyString()
                )
            ) doReturn flowOf(
                null
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

    @ExperimentalCoroutinesApi
    @Test
    fun testThrowError_OnLoginFailed() {
        //Arrange
        `when`(LoginValidator.isEmailValid(anyString())).thenAnswer { true }
        `when`(LoginValidator.isPasswordValid(anyString())).thenAnswer { true }

        // here we can assume any kind of exception, due to that login fails
        val error = RuntimeException()

        coroutineTestRule.testDispatcher.runBlockingTest {
            `when`(
                loginUserRepository.getLoginUserByCredentials(
                    anyString(),
                    anyString()
                )
            ).thenThrow(
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


    @ExperimentalCoroutinesApi
    @Test
    fun testSaveLoginUser(){

        coroutineTestRule.testDispatcher.runBlockingTest {
                //Arrange
                `when`(loginUserRepository.saveLoginUser()).thenAnswer { listOf<LoginUser>() }

                //Act
                loginViewModel.saveLoginUser()

                //Assert
                verify(loginUserRepository, times(1)).saveLoginUser()
            }

    }


    @After
    @Throws(Exception::class)
    fun tearDownClass() {
        loginValidator.close()
        loginViewModel.loginStateLiveData.removeObserver(mockObserverForStates)
    }

    companion object {
        private const val EMAIL = "amol@gmail.com"
        private const val PASSWORD = "123456"
    }

}





