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
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@RunWith(PowerMockRunner::class)
@PrepareForTest(UtilityClass::class)
class MockingStaticMethodTest {

    @Mock
    lateinit var dependency: Dependency

    @InjectMocks
    lateinit var systemUnderTest: SystemUnderTest

    @BeforeTest
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test retrieve using a mock`() {
        val stats = listOf(1, 2, 3)
        `when`(dependency.retrieveAllStats()).thenReturn(stats)
        PowerMockito.mockStatic(UtilityClass::class.java)
        `when`(UtilityClass.staticMethod(6)).thenReturn(150)
        val result = systemUnderTest.methodCallingAStaticMethod()

        //1
        assertEquals(150, result)
        //2
        PowerMockito.verifyStatic(UtilityClass::class.java)
        UtilityClass.staticMethod(6)
    }
}