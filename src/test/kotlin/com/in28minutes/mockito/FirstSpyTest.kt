package com.in28minutes.mockito

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.mockito.Mockito.spy
import kotlin.test.assertEquals

class FirstSpyTest {

    @Test
    fun `test that spy is similar to regular object`() {
        val arrayListSpy = spy(ArrayList<Any>()::class.java)
        assertEquals(0, arrayListSpy.size)
        arrayListSpy.add("Dummy")
        assertEquals(1, arrayListSpy.size)
    }

    @Test
    fun `test that spy can also do as mock`() {
        val arrayListSpy = spy(ArrayList<Any>()::class.java)
        assertEquals(0, arrayListSpy.size)

        given(arrayListSpy.size).willReturn(10)
        arrayListSpy.add("Dummy1")
        arrayListSpy.add("Dummy2")
        // though we add only one item, since spy is stubbed, it always returns 10
        assertEquals(10, arrayListSpy.size)
    }

    @Test
    fun `test verify in spy`() {
       val arrayListSpy = spy(ArrayList<String>()::class.java)
        arrayListSpy.add("Dummy")
        verify(arrayListSpy).add("Dummy")
        verify(arrayListSpy, never()).clear()
    }
}