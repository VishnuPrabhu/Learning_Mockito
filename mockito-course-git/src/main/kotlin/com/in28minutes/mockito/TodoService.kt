package com.in28minutes.mockito

// External Service - Lets say this comes from WunderList
interface TodoService {
    fun retrieveTodos(user: String): List<String>

    fun deleteTodo(todo: String)
}