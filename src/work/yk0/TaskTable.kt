package work.yk0

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.Column
import java.time.LocalDateTime

object TaskTable: LongIdTable() {
    val title: Column<String> = text("title")
    val description:Column<String> = text("description")
    val createdDateTime = datetime("created_date_time")
    val updatedDateTime = datetime("updated_date_time")
    val isCompleted: Column<Boolean> = bool("completed")
}