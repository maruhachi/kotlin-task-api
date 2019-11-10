package work.yk0

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.time.LocalDateTime
import java.time.ZoneId

class ExposedTaskRepository: TaskRepository {
    override fun updateTask(task: Task) {
        transaction {
            TaskTable.update(
                // 更新対象を絞る段
                { TaskTable.id eq task.id } )
            {
                // 更新内容を書く段
                it[title] = task.title
                it[description] = task.description
                it[isCompleted] = task.isCompleted
                it[createdDateTime] = task.createdDateTime.toJodaDateTime()
                it[updatedDateTime] = task.updatedDateTime.toJodaDateTime()
            }
        }
    }

    override fun findById(id: Long): Task? {
        return transaction {
            TaskTable.select{ TaskTable.id eq id }
                .map {
                    toTask(it)
                }
            }.firstOrNull()
        }


    override fun findAll(): List<Task> {
        return transaction {
            TaskTable.selectAll()
                .orderBy(TaskTable.createdDateTime, SortOrder.DESC)
                .map {
                    toTask(it)
                }
        }
    }

    override fun insertTask(task: Task) {
        transaction {
            TaskTable.insert {
                it[title] = task.title
                it[description] = task.description
                it[isCompleted] = task.isCompleted
                it[createdDateTime] = task.createdDateTime.toJodaDateTime()
                it[updatedDateTime] = task.updatedDateTime.toJodaDateTime()
            }
        }
    }

    private fun toTask(resultRow: ResultRow): Task{
        return Task(
            id = resultRow[TaskTable.id].value,
            title = resultRow[TaskTable.title],
            description = resultRow[TaskTable.description],
            createdDateTime = resultRow[TaskTable.createdDateTime].toStandardLocalDateTime(),
            updatedDateTime = resultRow[TaskTable.updatedDateTime].toStandardLocalDateTime(),
            isCompleted = resultRow[TaskTable.isCompleted]
        )
    }

    private fun DateTime.toStandardLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(toDate().toInstant(), ZoneId.systemDefault())
    }

    private fun LocalDateTime.toJodaDateTime(): DateTime {
        return DateTime(atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }

}