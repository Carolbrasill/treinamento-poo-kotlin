class `CourseCatalog(atv09,10,11,12,13,14,15)` {
    private val courses = mutableListOf<`Course(atv02,03,04)`>()
    private val trails = mutableListOf<`Trail(atv05,06,07,08)`>()

    fun registerCourse(course: `Course(atv02,03,04)`): Boolean {
        if (courses.any { it.code.equals(course.code, ignoreCase = true) }) return false
        return courses.add(course)
    }

    fun registerTrail(trail: `Trail(atv05,06,07,08)`): Boolean {
        if (trails.any { it.id == trail.id }) return false
        return trails.add(trail)
    }

    fun addCourseToTrail(courseCode: String, trailId: Int): Boolean {
        val course = findCourseByCode(courseCode) ?: return false
        val trail = findTrailById(trailId) ?: return false
        return trail.addCourse(course)
    }

    fun getAllCourses(): List<`Course(atv02,03,04)`> = courses.toList()
    fun getAllTrails(): List<`Trail(atv05,06,07,08)`> = trails.toList()

    fun findCourseByCode(code: String): `Course(atv02,03,04)`? =
        courses.find { it.code.equals(code, ignoreCase = true) }

    fun findTrailById(id: Int): `Trail(atv05,06,07,08)`? =
        trails.find { it.id == id }

    fun getCoursesByLevel(level: `CourseLevel(atv01)`): List<`Course(atv02,03,04)`> {
        return courses.filter { it.level == level }
    }
}