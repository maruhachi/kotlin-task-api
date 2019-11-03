package work.yk0

import io.ktor.application.ApplicationCall
import io.ktor.response.respond

class TaskController(val taskRepository: TaskRepository){

    suspend fun index(call: ApplicationCall){
        val list = this.taskRepository.findAll()
        call.respond(list)
    }
}