package com.in28minutes.mockito

import org.junit.Test
import kotlin.test.assertEquals

class TodoBusinessImplStubTest {

    @Test
    fun `test retrieve using a stub`() {
        val todoService = TodoServiceStub()
        val todoBusinessImpl = TodoBusinessImpl(todoService)
        val filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy")
        assertEquals(2, filteredTodos.size)
    }

    @Test
    fun `test retrieve when empty`() {
        // need to add stub for empty also, so stub is costly for different scenarios
    }
}