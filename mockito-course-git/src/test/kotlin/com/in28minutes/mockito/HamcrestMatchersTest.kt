package com.in28minutes.mockito

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Test

class  HamcrestMatchersTest {

    @Test
    fun test() {
        val scores = listOf(99, 100, 101, 105)
        //scores has 4 items
        assertThat(scores, hasSize(4))
        assertThat(scores, hasItems(99, 100))

        //every item: > 90
        assertThat(scores, everyItem(greaterThan(90)))
        assertThat(scores, everyItem(lessThan(150)))

        //String
        assertThat("", isEmptyString())
        assertThat(null, isEmptyOrNullString())

        //Arrays
        val marks = arrayOf(1, 2, 3)
        assertThat(marks, arrayWithSize(3))
        assertThat(marks, arrayContainingInAnyOrder(2, 1, 3))
    }
}