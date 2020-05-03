package com.in28minutes.mockito

import java.util.*


class TodoBusinessImpl internal constructor(private val todoService: TodoService) {

    fun retrieveTodosRelatedToSpring(user: String): List<String> {
        val filteredTodos: MutableList<String> = ArrayList()
        val allTodos: List<String> = todoService.retrieveTodos(user)
        for (todo in allTodos) {
            if (todo.contains("Spring")) {
                filteredTodos.add(todo)
            }
        }
        return filteredTodos
    }

    fun deleteTodosNotRelatedToSpring(user: String) {
        val allTodos: List<String> = todoService.retrieveTodos(user)
        for (todo in allTodos) {
            if (!todo.contains("Spring")) {
                todoService.deleteTodo(todo)
            }
        }
    }

}