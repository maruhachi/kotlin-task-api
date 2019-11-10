package work.yk0

interface TaskRepository {
    /** 全件取得 */
    fun findAll(): List<Task>

    /** タスク登録 */
    fun insertTask(task: Task)

    fun findById(id: Long): Task?

    fun updateTask(task: Task)

}