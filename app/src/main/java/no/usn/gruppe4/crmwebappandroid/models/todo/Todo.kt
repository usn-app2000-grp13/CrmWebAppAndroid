package no.usn.gruppe4.crmwebappandroid.models.todo

import java.util.*

data class Todo (
    //val _id: String?,
    //val date: Date?,
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

    data class addTodo(
        val todos: Todo
    )
}