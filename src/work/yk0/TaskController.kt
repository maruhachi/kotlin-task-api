package work.yk0

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import java.time.LocalDateTime
import javax.security.auth.login.AppConfigurationEntry

class TaskController(val taskRepository: TaskRepository){

    suspend fun index(call: ApplicationCall){
        val list = this.taskRepository.findAll()
        call.respond(list)
    }

    suspend fun create(call: ApplicationCall){
        val body = call.receive<TaskCreateBody>()
        val task = Task(
            id = null,
            title = body.title,
            description = body.description,
            createdDateTime = LocalDateTime.now(),
            updatedDateTime = LocalDateTime.now(),
            isCompleted = false
        )
        taskRepository.insertTask(task)
        call.response.status(HttpStatusCode.Created)
    }

    suspend fun show(call: ApplicationCall){
        val id: Long? = call.parameters["id"]?.toLongOrNull()
        val task: Task? = id?.let { taskRepository.findById(id) }
        if (task == null) {
            call.response.status(HttpStatusCode.NotFound)
        }else {
            call.respond(task)
        }
    }

    suspend fun update(call: ApplicationCall){
        val id: Long? = call.parameters["id"]?.toLongOrNull()
        val task: Task? = id?.let { taskRepository.findById(id) }
        if (task == null) {
            call.response.status(HttpStatusCode.NotFound)
        }else {
            val body = call.receive<TaskUpdateBody>()
            task.title = body.title
            task.description = body.description
            task.isCompleted = body.isCompleted
            task.updatedDateTime = LocalDateTime.now()
            taskRepository.updateTask(task)
            call.response.status(HttpStatusCode.NoContent)
        }
    }


}