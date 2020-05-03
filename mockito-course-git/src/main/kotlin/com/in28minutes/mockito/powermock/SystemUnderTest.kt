package com.in28minutes.mockito.powermock

import java.util.*


interface Dependency {
    fun retrieveAllStats(): List<Int>
}

class SystemUnderTest {
    private val dependency: Dependency? = null

    fun methodUsingAnArrayListConstructor(): Int {
        val list: ArrayList<*> = ArrayList<Any?>()
        return list.size
    }

    fun methodCallingAStaticMethod(): Int {
        //privateMethodUnderTest calls static method SomeClass.staticMethod
        val stats = dependency!!.retrieveAllStats()
        var sum: Long = 0
        for (stat in stats) sum += stat.toLong()
        return UtilityClass.staticMethod(sum)
    }

    private fun privateMethodUnderTest(): Long {
        val stats = dependency!!.retrieveAllStats()
        var sum: Long = 0
        for (stat in stats) sum += stat.toLong()
        return sum
    }
}