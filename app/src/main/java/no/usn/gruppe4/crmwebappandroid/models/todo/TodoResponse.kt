package no.usn.gruppe4.crmwebappandroid.models.todo

data class TodoResponse(
    val `data`: Data,
    val success: Boolean
){
    data class Data(
        val _id: String,
        val todos: List<Todo>
    )
}