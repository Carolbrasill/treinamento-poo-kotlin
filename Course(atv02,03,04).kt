data class `Course(atv02,03,04)`(
    val code: String,
    val title: String,
    val durationHours: Int,
    val level: `CourseLevel(atv01)`
) {
    init {
        require(code.isNotBlank()) { "O código do curso não pode estar em branco." }
        require(title.isNotBlank()) { "O título do curso não pode estar em branco." }
        require(durationHours > 0) { "A duração em horas deve ser maior que zero." }
    }
}