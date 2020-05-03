package com.in28minutes.mockito

import com.nhaarman.mockitokotlin2.capture
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.mock

class TodoBusinessImplBDDMockTest {

    @Test
    fun `test retrieve using BDD mock`() {
        //Given
        val todoServiceMock = mock(TodoService::class.java)
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(
            listOf("Learn Spring MVC", "Learn Spring", "Learn Dance")
        )
        val todoBusinessImpl = TodoBusinessImpl(todoServiceMock)
        //When
        val filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy")
        //Then
        assertThat(filteredTodos.size, `is`(2))
    }

    @Test
    fun `lets mock List get() using BDD`() {
        //Given
        val listMock = mock(ArrayList<String>()::class.java)
        given(listMock[anyInt()]).willReturn("in28Minutes")
        //When
        val value: String = listMock[0]
        //Then
        assertThat(value, `is`("in28Minutes"))
    }

    @Test
    fun `test delete TODO not related to sprint using Verify`() {
        //Given
        val todoServiceMock = mock(TodoService::class.java)
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(
            listOf("Learn Spring MVC", "Learn Spring", "Learn Dance")
        )
        val todoBusinessImpl = TodoBusinessImpl(todoServiceMock)
        //When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy")
        //Then
        //verify(todoServiceMock).deleteTodo("Learn Dance")
        //verify(todoServiceMock, times(1)).deleteTodo("Learn Dance")
        then(todoServiceMock).should().deleteTodo("Learn Dance")

        //verify(todoServiceMock, Mockito.never()).deleteTodo("Learn Spring MVC")
        //verify(todoServiceMock, Mockito.never()).deleteTodo("Learn Spring")
        then(todoServiceMock).should(never()).deleteTodo("Learn Spring")
    }

    @Test
    fun `test delete TODO not related to sprint using Argument Captor`() {
        //Declare -> Define -> Capture -> Assert
        val argumentCaptor = ArgumentCaptor.forClass(String::class.java)
        //Given
        val todoServiceMock = mock(TodoService::class.java)
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(
            listOf("Learn Spring MVC", "Learn Spring", "Learn Dance", "Learn English")
        )
        val todoBusinessImpl = TodoBusinessImpl(todoServiceMock)
        //When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy")
        //Then
        then(todoServiceMock).should(Mockito.times(2)).deleteTodo(capture(argumentCaptor))
        //Assert
        assertThat(argumentCaptor.allValues, `is`(listOf("Learn Dance", "Learn English")))
    }

}