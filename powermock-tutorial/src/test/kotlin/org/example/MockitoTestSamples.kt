package org.example

import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.*
import org.mockito.exceptions.verification.NoInteractionsWanted
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.assertEquals


class MockitoTestSamples {

    @Before
    fun beforeEachTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun`14 - Changing default return values of unstubbed invocations`() {
        class YourOwnAnswer: Answer<Foo> {
            override fun answer(p0: InvocationOnMock?): Foo {
                return Foo()
            }
        }

        //It is the default answer so it will be used only when you don't stub the method call.
        val mock1 = mock(Foo::class.java, RETURNS_SMART_NULLS)
        val mockTwo = mock(Foo::class.java, YourOwnAnswer())

        // This is just an example of using the Answer Interface, and has nothing to do with Foo creation.
        val mockThree = mock(Foo::class.java) {
            val arguments = it.arguments
            if (arguments.isNotEmpty()) {
                Foo()
            } else {
                throw IllegalArgumentException("Call Foo with arguemnts")
            }
        }
    }

    @Test
    fun`15 - Capturing arguments for further assertions`() {
        val mock = mock(Person::class.java)
        val argument = ArgumentCaptor.forClass(String::class.java)

        mock.doSomething("John")

        // 1. verfiy this method is called on the mock
        verify(mock).doSomething(argument.capture())
        // 2. assert the value used for the argument is "John"
        assertEquals("John", argument.value)
    }

    @Test
    fun`16 - Real partial mocks `() {
        val list: List<*> = spy(LinkedList<Any?>())

        //you can enable partial mock capabilities selectively on mocks:
        val mock = mock(Foo::class.java)
        //Be sure the real implementation is 'safe'.
        //If real implementation throws exceptions or depends on specific state of the object then you're in trouble.
        `when`(mock.someMethod()).thenCallRealMethod()
        mock.someMethod()
    }

    @Test
    fun`17 - Resetting mocks`() {
        val mock = mock(ArrayList<Int>()::class.java)
        `when`(mock.size).thenReturn(10)
        mock.add(1)

        println("Size ${mock.size}")

        reset(mock);
        //at this point the mock forgot any interactions & stubbing
        println("Reset Size ${mock.size}")
    }

    @Test
    fun`18 - Troubleshooting & validating framework usage`() {
//        First of all, in case of any trouble, I encourage you to read the Mockito FAQ: https://github.com/mockito/mockito/wiki/FAQ
//        In case of questions you may also post to mockito mailing list: http://groups.google.com/group/mockito
//
//        Next, you should know that Mockito validates if you use it correctly all the time. However, there's a gotcha so please read the javadoc for validateMockitoUsage()

        val mock = mock(ArrayList<Int>()::class.java)
        //Oops, thenReturn() part is missing:
        `when`(mock[0])
        //Oops, verified method call is inside verify() where it should be on the outside:
        verify(mock.size)
        //Oops, missing method to verify:
        verify(mock)

        validateMockitoUsage()
    }

    @Test
    fun`use mock withSettings`() {
        //Creates mock with different default answer & name
        val mock = mock(Foo::class.java, withSettings()
            .defaultAnswer(RETURNS_SMART_NULLS)
            .name("cool mockie!"))

        //Creates mock with different default answer, descriptive name and extra interfaces
        val mock2 = mock(Foo::class.java, withSettings()
            .defaultAnswer(RETURNS_SMART_NULLS)
            .name("cool mockie!")
            .extraInterfaces(Bar::class.java))
        print(mock2)
    }

    @Test
    fun`19 - Aliases for behavior driven development`() {
        val seller = mock(Seller::class.java)
        val shop = mock(Shop::class.java)

        // TODO("I need to spend more time in this")
//        //given
//        given(seller.askForBread()).willReturn(Bread())
//        //when
//        val goods = shop.buyBread()
//        //then
//        assertThat(goods, bread)
    }

    @Test
    fun`20 - Serializable mocks`() {
        //WARNING: This should be rarely used in unit testing.
        val serializableMock = mock(ArrayList::class.java, withSettings().serializable())

        //Making a real object spy serializable is a bit more effort as the spy(...) method does not have an overloaded version which accepts MockSettings.
        val list = ArrayList<Any>()
        val serializableSpy = mock(ArrayList::class.java, withSettings()
            .spiedInstance(list)
            .defaultAnswer(CALLS_REAL_METHODS)
            .serializable())
    }

    @Test
    fun`22 - Verification with timeout`() {
        val mock = mock(Foo::class.java)
        mock.someMethod()

        verify(mock).someMethod()
        verify(mock, timeout(100)).someMethod()
        verify(mock, timeout(100).times(1)).someMethod()
    }

    @Test
    fun`23 - Automatic instantiation of @Spies, @InjectMocks and constructor injection goodness`() {
//        Mockito will now try to instantiate @Spy and will instantiate @InjectMocks fields using constructor injection, setter injection, or field injection.
//
//        To take advantage of this feature you need to use MockitoAnnotations.initMocks(Object), MockitoJUnitRunner or MockitoRule.
//
//        Read more about available tricks and the rules of injection in the javadoc for InjectMocks
    }

    @Test
    fun`24 - One-liner stubs`() {
        val fooMock = `when`(mock(Foo::class.java).someMethod()).thenReturn("calling someMethod").getMock<Foo>()
        val fooResult = fooMock.someMethod()
        assertEquals("calling someMethod", fooResult)
    }

    @Test
    fun`25 - Verification ignoring stubs`() {
        //mocking lists for the sake of the example (if you mock List in real you will burn in hell)
        val mock1 = mock(ArrayList::class.java)
        val mock2 = mock(ArrayList::class.java)

        //stubbing mocks:
        `when`(mock1[0]).thenReturn(10)
        `when`(mock2[0]).thenReturn(20)

        //using mocks by calling stubbed get(0) methods:
        println(mock1[0]); //prints 10
        println(mock2[0]); //prints 20

        //using mocks by calling clear() methods:
        mock1.clear()
        mock2.clear()

        //verification:
        verify(mock1).clear()
        verify(mock2).clear()

        //verifyNoMoreInteractions() fails because get() methods were not accounted for.
        try {
            verifyNoMoreInteractions(mock1, mock2);
        } catch (e: NoInteractionsWanted) {
            println("Got Exception")
        }

        //However, if we ignore stubbed methods then we can verifyNoMoreInteractions()
        ignoreStubs(mock1, mock2)
        verifyNoMoreInteractions(mock1, mock2);
    }

    @Test
    fun`26 - Mocking details`() {
        val mock = mock(ArrayList::class.java)

        //To identify whether a particular object is a mock or a spy:
        Mockito.mockingDetails(mock).isMock
        Mockito.mockingDetails(mock).isSpy

        //Getting details like type to mock or default answer:
        val details = mockingDetails(mock)
        details.mockCreationSettings.typeToMock
        details.mockCreationSettings.defaultAnswer

        //Getting invocations and stubbings of the mock:
        details.invocations
        details.stubbings

        //Printing all interactions (including stubbing, unused stubs)
        println(details.printInvocations())
    }

    @Test
    fun`27 - Delegate calls to real instance`() {
//        An example with a final class that we want to delegate to:
//
//        final class DontYouDareToMockMe implements list { ... }
//
//        DontYouDareToMockMe awesomeList = new DontYouDareToMockMe();
//
//        List mock = mock(List.class, delegatesTo(awesomeList));
//
//        This feature suffers from the same drawback as the spy. The mock will call the delegate if you use regular when().then() stubbing style. Since the real implementation is called this might have some side effects. Therefore you should to use the doReturn|Throw|Answer|CallRealMethod stubbing style. Example:
//
//
//        List listWithDelegate = mock(List.class, AdditionalAnswers.delegatesTo(awesomeList));
//
//        //Impossible: real method is called so listWithDelegate.get(0) throws IndexOutOfBoundsException (the list is yet empty)
//        when(listWithDelegate.get(0)).thenReturn("foo");
//
//        //You have to use doReturn() for stubbing
//        doReturn("foo").when(listWithDelegate).get(0);
    }

    @Test
    fun`28 - MockMaker API`() {
//        Driven by requirements and patches from Google Android guys Mockito now offers an extension point that allows replacing the proxy generation engine. By default, Mockito uses Byte Buddy to create dynamic proxies.
//
//        The extension point is for advanced users that want to extend Mockito. For example, it is now possible to use Mockito for Android testing with a help of dexmaker.
    }

    @Mock
    lateinit var fuelType: Battery
    @InjectMocks
    lateinit var car: Car
    @Test
    fun`Bdd style verification of mock behavior`() {
        //given
        given(fuelType.isElectric()).willReturn(true)
        //when
        car.refuel()
        //then
        then(fuelType).should().isElectric()
    }
}