package work.yk0

import java.time.LocalDateTime

class Task(
    var title: String,
    var description: String,
    var createdDatetime: LocalDateTime,
    var updatedDateTime: LocalDateTime,
    var id: Long?,
    var isCompleted: Boolean )