package org.example

import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import java.util.*
import kotlin.collections.ArrayList


class HelloTest {

//    @get: Rule
//    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun `1 - Let's verify some behaviour!`() {
        val mockedList = mock(ArrayList<String>()::class.java)
        mockedList.add("one")
        mockedList.clear()

        verify(mockedList).add("one")
        verify(mockedList).clear()
    }

    @Test(expected = RuntimeException::class)
    fun `2 - How about some stubbing?`() {
        val mockedList = mock(LinkedList::class.java)

        `when`(mockedList[0]).thenReturn("first")
        `when`(mockedList[1]).thenThrow(RuntimeException())

        println(mockedList[0])
        println(mockedList[1])

        verify(mockedList)[0]
    }

    @Test
    fun`Argument matchers`() {
        val mockedList = mock(ArrayList<String>()::class.java)
        `when`(mockedList[anyInt()]).thenReturn("element")
        `when`(mockedList.contains(argThat { argument -> argument.isNotEmpty() })).thenReturn(true)

        println(mockedList[999])
        verify(mockedList)[anyInt()]

        mockedList.add("string greter than 5 digits")

        verify(mockedList).add(argThat { someString -> someString.length > 5 })
    }


    @Test
    fun`Verifying exact number of invocations - at least x - never`() {
        val mockedList = mock(ArrayList<String>()::class.java)

        mockedList.add("one")

        mockedList.add("twice")
        mockedList.add("twice")

        mockedList.add("three times")
        mockedList.add("three times")
        mockedList.add("three times")

        verify(mockedList).add("one")
        verify(mockedList, times(1)).add("one")

        verify(mockedList, times(2)).add("twice")
        verify(mockedList, times(3)).add("three times")

        verify(mockedList, never()).add("never happened")

        verify(mockedList, atMostOnce()).add("one")
        verify(mockedList, atLeastOnce()).add("three times")
        verify(mockedList, atLeast(2)).add("twice")
        verify(mockedList, atMost(5)).add("three times")
    }

    @Test(expected = RuntimeException::class)
    fun`Stubbing void methods with exceptions`() {
        val mockedList = mock(ArrayList<String>()::class.java)

        doThrow(RuntimeException()).`when`(mockedList).clear()

        mockedList.clear()
    }

    @Test
    fun`Verification in order`() {
        val singleMock = mock(ArrayList<String>()::class.java)

        singleMock.add("was added first")
        singleMock.add("was added second")

        var inOrder = inOrder(singleMock)

        inOrder.verify(singleMock).add("was added first")
        inOrder.verify(singleMock).add("was added second")

        // B. Multiple mocks that must be used in a particular order
        // B. Multiple mocks that must be used in a particular order
        val firstMock = mock(ArrayList<String>()::class.java)
        val secondMock = mock(ArrayList<String>()::class.java)

        //using mocks
        firstMock.add("was called first")
        secondMock.add("was called second")

        inOrder = inOrder(firstMock, secondMock)

        inOrder.verify(firstMock).add("was called first")
        inOrder.verify(secondMock).add("was called second")
    }

    @Test
    fun`Making sure interaction(s) never happened on mock`() {
        val mockOne = mock(ArrayList<String>()::class.java)
        val mockTwo = mock(ArrayList<String>()::class.java)

        mockOne.add("one")

        verify(mockOne).add("one")

        verify(mockOne, never()).add("never")

        verifyNoMoreInteractions(mockOne)
        verifyNoInteractions(mockTwo)
    }

    @Test
    fun`Finding redundant invocations`() {
        val mockedList = mock(ArrayList<String>()::class.java)

        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");

        //following verification will fail
        verifyNoMoreInteractions(mockedList);
        //or
        //verify(mockedList, never()).add(ArgumentMatchers.any())
    }

    @Mock
    lateinit var shortHandMock: ArrayList<String>
    @Test
    fun`Shorthand for mocks creation - @Mock annotation`() {
        MockitoAnnotations.initMocks(this)
        println(shortHandMock.size)
    }

    @Test
    fun`Stubbing consecutive calls (iterator-style stubbing)`() {
        val mockedList = mock(ArrayList<String>()::class.java)

        `when`(mockedList[anyInt()])
                .thenReturn("One")
                .thenReturn("Two")

        println(mockedList[anyInt()])
        println(mockedList[anyInt()])
        println(mockedList[anyInt()])
    }

    @Test
    fun`Stubbing with callbacks`() {
        val mockedList = mock(ArrayList<String>()::class.java)

        `when`(mockedList[anyInt()]).thenAnswer {
            invocation: InvocationOnMock ->
                val args = invocation.arguments
                val mock = invocation.mock
                return@thenAnswer "called using ($mock) with arguments: " + Arrays.toString(args)
        }

        print(mockedList[0])
    }

    @Test(expected = RuntimeException::class)
    fun`Stubbing void methods requires a different approach`() {
        val mockedList = mock(ArrayList<String>()::class.java)

        doThrow(RuntimeException()).`when`(mockedList).clear()

        mockedList.clear()
    }

    @Test
    fun`Stubbing void methods requires a different approach - do call real method`() {
        val mock = mock(Foo::class.java)
        doCallRealMethod().`when`(mock).someVoidMethod()
        mock.someVoidMethod()
    }

    @Test
    fun`Spying on real objects`() {
        val spy = spy(ArrayList<String>()::class.java)

        `when`(spy.size).thenReturn(100)

        spy.add("one")
        spy.add("two")

        println(spy[0])

        println(spy.size)

        verify(spy).add("one")
        verify(spy).add("two")

        //Important gotcha on spying real objects!

        val list: List<*> = LinkedList<Any?>()
        val spy2 = spy(list)
        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        //`when`(spy[0]).thenReturn("foo")
        doReturn("foo").`when`(spy2)[0]
        println(spy2[0])
    }

}
