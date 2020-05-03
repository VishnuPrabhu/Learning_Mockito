package com.in28minutes.mockito

import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class TodoBusinessImplMockTest {

    @Test
    fun `test retrieve using a mock`() {
        val todoServiceMock = mock(TodoService::class.java)
        `when`(todoServiceMock.retrieveTodos("Dummy")).thenReturn(
            listOf("Learn Spring MVC", "Learn Spring", "Learn Dance")
        )
        val todoBusinessImpl = TodoBusinessImpl(todoServiceMock)
        val filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy")
        assertEquals(2, filteredTodos.size)
    }

    @Test
    fun `test retrieve using a mock empty list`() {
        val todoServiceMock = mock(TodoService::class.java)
        `when`(todoServiceMock.retrieveTodos("Dummy")).thenReturn(
            emptyList()
        )
        val todoBusinessImpl = TodoBusinessImpl(todoServiceMock)
        val filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy")
        assertEquals(0, filteredTodos.size)
    }
}