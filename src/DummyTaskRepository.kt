package work.yk0

import java.time.LocalDateTime

class DummyTaskRepository: TaskRepository{
    override fun findAll(): List<Task> {
        val tasks = listOf(
            Task(
                title = "自転車",
                description = "6万円くらい",
                createdDateTime = LocalDateTime.now(),
                updatedDateTime = LocalDateTime.now(),
                id = 100,
                isCompleted = false
            ),
            Task(
                title = "new スマホ",
                description = "iPhone 11 Pro",
                createdDateTime = LocalDateTime.now(),
                updatedDateTime = LocalDateTime.now(),
                id = 101,
                isCompleted = true
            )
        )
        return tasks
    }
}