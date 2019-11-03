package work.yk0

interface TaskRepository {
    /** 全件取得 */
    fun findAll(): List<Task>

}