package com.in28minutes.mockito

import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class TodoBusinessImplMockitoInjectMockTest {

//    @Rule @JvmField
//    val mockitoRule = MockitoJUnit.rule()

    @BeforeTest
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var todoServiceMock: TodoService

    @InjectMocks
    lateinit var todoBusinessImpl: TodoBusinessImpl

    @Captor
    lateinit var stringArgumentCaptor: ArgumentCaptor<String>

    @Test
    fun `test retrieve using a mock`() {
        `when`(todoServiceMock.retrieveTodos("Dummy")).thenReturn(
            listOf("Learn Spring MVC", "Learn Spring", "Learn Dance")
        )
        val filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy")
        assertEquals(2, filteredTodos.size)
    }

    @Test
    fun `test retrieve using a mock empty list`() {
        `when`(todoServiceMock.retrieveTodos("Dummy")).thenReturn(
            emptyList()
        )
        val filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy")
        assertEquals(0, filteredTodos.size)
    }

    @Test
    fun `test retrieve using a mock argument captor`() {
        `when`(todoServiceMock.retrieveTodos("Dummy")).thenReturn(
            listOf("Learn Spring MVC", "Learn Spring", "Learn Dance")
        )
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy")
        verify(todoServiceMock).deleteTodo(capture(stringArgumentCaptor))
        assertThat(stringArgumentCaptor.value, `is`("Learn Dance"))
    }
}