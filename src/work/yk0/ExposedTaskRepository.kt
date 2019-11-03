package work.yk0

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.time.LocalDateTime
import java.time.ZoneId

class ExposedTaskRepository: TaskRepository {
    override fun findAll(): List<Task> {
        return transaction {
            TaskTable.selectAll()
                .orderBy(TaskTable.createdDateTime, SortOrder.DESC)
                .map {
                    Task(
                        id = it[TaskTable.id].value,
                        title = it[TaskTable.title],
                        description = it[TaskTable.description],
                        createdDateTime = it[TaskTable.createdDateTime].toStandardLocalDateTime(),
                        updatedDateTime = it[TaskTable.updatedDateTime].toStandardLocalDateTime(),
                        isCompleted = it[TaskTable.isCompleted]
                    )
                }
        }
    }
    private fun DateTime.toStandardLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(toDate().toInstant(), ZoneId.systemDefault())
    }

    private fun LocalDateTime.toJodaDateTime(): DateTime {
        return DateTime(atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }

}