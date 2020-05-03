package com.in28minutes.mockito.powermock

object UtilityClass {
    fun staticMethod(value: Long): Int {
        // Some complex logic is done here...
//        throw RuntimeException(
//            "I dont want to be executed. I will anyway be mocked out."
//        )
        print("This is wrong")
        return 90
    }
}