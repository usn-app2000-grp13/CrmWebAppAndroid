package no.usn.gruppe4.crmwebappandroid.models.todo

import no.usn.gruppe4.crmwebappandroid.models.IdRequest

data class Todo  (
    val _id: String?,
    val description: String?,
    var completed: Boolean = false
){
    data class IdTodo(
        val _id: String
    )

    data class SetComplete(
        val _id: String,
        val todos: IdTodo
    )

    data class AddTodo(
        val todos: Todo,

    )

    data class DeleteTodo(
        val _id: String,
        val todos: IdRequest
    )
}