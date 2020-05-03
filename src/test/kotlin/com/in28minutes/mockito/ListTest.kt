package com.in28minutes.mockito

import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito.*
import java.lang.RuntimeException
import kotlin.test.assertEquals

class ListTest {

    @Test
    fun `lets mock list size method`() {
        val listMock = mock(List::class.java)
        `when`(listMock.size).thenReturn(2)
        assertEquals(2, listMock.size)
    }

    @Test
    fun `lets mock list size return multiple methods`() {
        val listMock = mock(List::class.java)
        `when`(listMock.size).thenReturn(2)
                             .thenReturn(3)
        assertEquals(2, listMock.size)
        assertEquals(3, listMock.size)
    }

    @Test
    fun `lets mock list get method`() {
        val listMock = mock(List::class.java)
        `when`(listMock[0]).thenReturn("in28Minutes")
            .thenReturn(3)
        assertEquals("in28Minutes", listMock[0])
        assertEquals(null, listMock[1])
    }

    @Test
    fun `lets mock list get method with anyInt`() {
        val listMock = mock(List::class.java)
        `when`(listMock[anyInt()]).thenReturn("in28Minutes")
        assertEquals("in28Minutes", listMock[1])
    }

    @Test(expected = RuntimeException::class)
    fun `lets mock list get method with exception`() {
        val listMock = mock(List::class.java)
        `when`(listMock[anyInt()]).thenThrow(RuntimeException::class.java)
        val throwError = listMock[0]
    }

    @Test
    fun `lets mock list get method with exception2`() {
        val listMock = mock(List::class.java)
        `when`(listMock.subList(anyInt(), eq(5))).thenThrow(RuntimeException::class.java)
        val throwError = listMock.subList(1, 5)
    }

    @Test
    fun `lets mock list get method to see unstubbed method calls`() {
        val listMock = mock(mutableListOf<Int>()::class.java)
        println(listMock.size)
        //println(listMock[0]) // throwing NullPointerException
        listMock[0] = 6
        //println(listMock[0]) // Again throwing NullPointerException, basically set is not working as it is a mock
        println(listMock.toString())
    }
}