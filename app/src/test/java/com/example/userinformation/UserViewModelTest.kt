package com.example.userinformation

import com.example.network.model.User
import com.example.network.repository.UserRepository
import com.example.userinformation.viewmodel.UserViewModel
import com.example.userinformation.viewmodel.UsersUiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)



    private val sampleUsers = listOf(
        User(id = 1, name = "Alice","","","","","","","","",""),
        User(id = 2, name = "Bob","","","","","","","","",""),
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loadUsers emits Loading first`() = testScope.runTest {
        // Arrange
        coEvery { repository.getUsers() } returns sampleUsers

        // Act
        viewModel = UserViewModel(repository)

        // Assert initial state is Loading
        val initialState = viewModel.uiState.value
        assertTrue(initialState is UsersUiState.Loading)
    }

    @Test
    fun `loadUsers emits Success when repository returns users`() = testScope.runTest {
        // Arrange
        coEvery { repository.getUsers() } returns sampleUsers

        // Act
        viewModel = UserViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle() // let coroutine finish

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersUiState.Success)
        assertEquals(sampleUsers, (state as UsersUiState.Success).users)
    }

    @Test
    fun `loadUsers emits Error when repository throws IOException`() = testScope.runTest {
        // Arrange
        coEvery { repository.getUsers() } throws IOException("No internet")

        // Act
        viewModel = UserViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersUiState.Error)
        assertEquals("No internet available", (state as UsersUiState.Error).message)
    }

    @Test
    fun `loadUsers emits Error when repository throws generic Exception`() = testScope.runTest {
        // Arrange
        coEvery { repository.getUsers() } throws Exception("Something went wrong")

        // Act
        viewModel = UserViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UsersUiState.Error)
        assertEquals("Something went wrong", (state as UsersUiState.Error).message)
    }
}
