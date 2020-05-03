package com.in28minutes.mockito

class TodoServiceStub: TodoService {
    override fun retrieveTodos(user: String): List<String> {
        return listOf("Learn Spring MVC", "Learn Spring", "Learn Dance")
    }

    override fun deleteTodo(todo: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}