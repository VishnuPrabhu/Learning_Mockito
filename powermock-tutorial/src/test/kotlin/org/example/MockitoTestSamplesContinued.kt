package org.example

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.*
import org.mockito.exceptions.verification.NoInteractionsWanted
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.mock.SerializableMode
import org.mockito.stubbing.Answer
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.assertEquals

class MockitoTestSamplesContinued {

    @get:Rule
    val mockito: MockitoRule = MockitoJUnit.rule()
//    @Before
//    fun beforeEachTestMethod() {
//        MockitoAnnotations.initMocks(this)
//    }

    @Test
    fun`30 - Spying or mocking abstract classes`() {
        //convenience API, new overloaded spy() method:
        val spy = spy(FuelType::class.java)

        //Mocking abstract methods, spying default methods of an interface (only available since 2.7.13)
        val function = spy(Function::class.java)

        //Robust API, via settings builder:
        val spy2 = mock(FuelType::class.java, withSettings()
            .useConstructor().defaultAnswer(CALLS_REAL_METHODS))

        //Mocking an abstract class with constructor arguments (only available since 2.7.14)
        val spy3 = mock(FuelType::class.java, withSettings()
            .useConstructor("arg1", 123).defaultAnswer(CALLS_REAL_METHODS))

        //Mocking a non-static inner abstract class:
        val spy4 = mock(FuelType::class.java, withSettings()
            .useConstructor().outerInstance(spy2).defaultAnswer(CALLS_REAL_METHODS))
    }

    @Test
    fun`31 - Mockito mocks can be serialized - deserialized across classloaders`() {
        // use regular serialization
        mock(Foo::class.java, withSettings().serializable())

        // use serialization across classloaders
        mock(Foo::class.java, withSettings().serializable(SerializableMode.ACROSS_CLASSLOADERS))
    }

    @Test
    fun`32 - Better generic support with deep stubs`() {
        val lines = mock(Lines::class.java, RETURNS_DEEP_STUBS)

        // Now Mockito understand this is not an Object but a Line
        val line = lines.iterator().next()

        //Please note that in most scenarios a mock returning a mock is wrong.
    }

    @Test
    fun`33 - Mockito JUnit rule `() {
//        @Rule val mockitoRule = MockitoJUnit.rule()
    }

    @Test
    fun`35 - Custom verification failure message`() {
        val mock = mock(Foo::class.java)

        verify(mock, description("Some method is not called")).someMethod()
    }

    @Test
    fun`36 - Java 8 Lambda Matcher Support`() {
        val mock = mock(ArrayList<String>()::class.java)
        `when`(mock[ArgumentMatchers.anyInt()]).thenReturn("This is a mock response")

        mock.add("Vishnu Prabhu")
        mock.add("Gayathri")

        verify(mock, times(2)).add(argThat { value -> value.length > 5 })

        println(mock[0])

        verify(mock).add(argThat { value -> value == "Vishnu Prabhu" })
    }

    @Test
    fun`37 - Java 8 Custom Answer Support`() {
        val mock = mock(Foo::class.java)
        doAnswer { invocationOnMock -> 12 }.`when`(mock).someMethod()
        val result = mock.someMethod()
        assertEquals(12, result)


        // answer by using one of the parameters - converting into the right
        // type as your go - in this case, returning the length of the second string parameter
        // as the answer. This gets long-winded quickly, with casting of parameters.
        val mock2 = mock(Person::class.java)
        doAnswer { invocation -> (invocation.getArgument(0) as String).length.toString() }.`when`(mock2).doSomethingAndReturnAString(any())
        val result2 = mock2.doSomethingAndReturnAString("Hello")
        println(result2)

//        // Example interface to be mocked has a function like:
//        void execute(String operand, Callback callback);
//
//        doAnswer(AdditionalAnswers.answerVoid((operand, callback) -> callback.receive("dummy"))
//        .when(mock).execute(anyString(), any(Callback.class));
    }

    @Test
    fun`38 - Meta data and generic type retention `() {
//        @MyAnnotation
//        class Foo {
//            List<String> bar() { ... }
//        }
//
//        Class<?> mockType = mock(Foo.class).getClass();
//        assert mockType.isAnnotationPresent(MyAnnotation.class);
//        assert mockType.getDeclaredMethod("bar").getGenericReturnType() instanceof ParameterizedType;
    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

//    @Test
//    fun``() {
//
//    }

}