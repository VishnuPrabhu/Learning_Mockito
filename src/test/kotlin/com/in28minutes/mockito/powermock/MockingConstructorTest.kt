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
@PrepareForTest(SystemUnderTest::class)
class MockingConstructorTest {

    @InjectMocks
    lateinit var systemUnderTest: SystemUnderTest

    @Mock
    lateinit var mockList: ArrayList<String>

    @BeforeTest
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test constructor mock`() {
        val stats = listOf(1, 2, 3)
        `when`(mockList.size).thenReturn(10)

        PowerMockito.whenNew(ArrayList::class.java).withAnyArguments().thenReturn(mockList)

        val size = systemUnderTest.methodUsingAnArrayListConstructor()

        assertEquals(10, size)
    }
}