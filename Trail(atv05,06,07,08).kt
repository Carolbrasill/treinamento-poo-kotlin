class `Trail(atv05,06,07,08)`(
    val id: Int,
    val name: String
) {
    private val _courses = mutableListOf<`Course(atv02,03,04)`>()
    val courses: List<`Course(atv02,03,04)`> get() = _courses.toList()

    val totalDurationHours: Int
        get() = _courses.sumOf { it.durationHours }

    init {
        require(id > 0) { "O ID da trilha deve ser maior que zero." }
        require(name.isNotBlank()) { "O nome da trilha não pode estar em branco." }
    }

    fun addCourse(course: `Course(atv02,03,04)`): Boolean {
        if (_courses.any { it.code == course.code }) return false
        return _courses.add(course)
    }

    fun removeCourse(courseCode: String): Boolean {
        return _courses.removeIf { it.code == courseCode }    }
}