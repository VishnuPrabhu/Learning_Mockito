package com.in28minutes.mockito.powermock

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.reflect.Whitebox
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@RunWith(PowerMockRunner::class)
class InvokingPrivateMethodTest {

    @Mock
    lateinit var dependency: Dependency

    @InjectMocks
    lateinit var systemUnderTest: SystemUnderTest

    @BeforeTest
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test retrieve for a private method`() {
        val stats = listOf(1, 2, 3)
        `when`(dependency.retrieveAllStats()).thenReturn(stats)

        val result: Long = Whitebox.invokeMethod(systemUnderTest, "privateMethodUnderTest")

        assertEquals(6, result)
    }
}