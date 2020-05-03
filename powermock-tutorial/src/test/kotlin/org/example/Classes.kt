package org.example

import java.lang.RuntimeException


open class Foo {
    fun someVoidMethod() {
        println("Real someVoidMethod is called")
    }

    fun someMethod(): Any {
        return println("Real someMethod is called")
    }
}

interface Bar {

}

class Person {

    fun doSomething(name: String?) {
        print("My Name is $name")
    }

    fun doSomethingAndReturnAString(name: String?) : String {
        return "Something has been done"
    }
}

class Shop {
    fun buyBread(): Goods = Bread()
}

class Seller {
    fun askForBread(): Goods {
        throw RuntimeException("you need to Stub this method")
    }
}

interface Goods

class Bread: Goods

class Car constructor(val fuelType: FuelType) {

    fun refuel() {
        when {
            fuelType.isElectric() -> println("Recharging the Battery")
            else -> println("Fueling with Gas")
        }
    }
}

abstract class FuelType {

    abstract fun isElectric(): Boolean
}

class Battery: FuelType() {
    override fun isElectric(): Boolean = true
}

class Line

class Lines: ArrayList<Line>()
