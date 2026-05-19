import java.util.Scanner

fun main() {
    val catalog = `CourseCatalog(atv09,10,11,12,13,14,15)`()
    val scanner = Scanner(System.`in`)
    var option = 0

    val cursoBase = `Course(atv02,03,04)`("KOT-101", "Kotlin Mobile", 40, `CourseLevel(atv01)`.BEGINNER)
    catalog.registerCourse(cursoBase)
    val trilhaBase = `Trail(atv05,06,07,08)`(1, "Formação Android")
    catalog.registerTrail(trilhaBase)
    catalog.addCourseToTrail("KOT-101", 1)

    do {
        println("\n=========================================================")
        println("         SISTEMA DE GERENCIAMENTO DE CATÁLOGO            ")
        println("=========================================================")
        println("1. Cadastrar Novo Curso")
        println("2. Cadastrar Nova Trilha")
        println("3. Adicionar Curso a uma Trilha")
        println("4. Listar Todos os Cursos")
        println("5. Listar Todas as Trilhas")
        println("6. Filtrar Cursos por Nível")
        println("7. Sair")
        print("Escolha uma opção: ")

        try {
            option = scanner.nextInt()
            scanner.nextLine()

            when (option) {
                1 -> {
                    println("\n--- CADASTRAR NOVO CURSO ---")
                    print("Código do Curso (ex: AND-201): ")
                    val code = scanner.nextLine()
                    print("Título do Curso: ")
                    val title = scanner.nextLine()
                    print("Duração em Horas: ")
                    val hours = scanner.nextInt()
                    println("Nível (1 - BEGINNER, 2 - INTERMEDIATE, 3 - ADVANCED): ")
                    val levelChoice = scanner.nextInt()

                    val level = when(levelChoice) {
                        1 -> `CourseLevel(atv01)`.BEGINNER
                        2 -> `CourseLevel(atv01)`.INTERMEDIATE
                        else -> `CourseLevel(atv01)`.ADVANCED
                    }

                    val newCourse = `Course(atv02,03,04)`(code, title, hours, level)
                    if (catalog.registerCourse(newCourse)) {
                        println("Curso cadastrado com sucesso!")
                    } else {
                        println("Erro: Já existe um curso com esse código.")
                    }
                }
                2 -> {
                    println("\n--- CADASTRAR NOVA TRILHA ---")
                    print("ID numérico da Trilha: ")
                    val id = scanner.nextInt()
                    scanner.nextLine()
                    print("Nome da Trilha (ex: Formação iOS): ")
                    val name = scanner.nextLine()

                    val newTrail = `Trail(atv05,06,07,08)`(id, name)
                    if (catalog.registerTrail(newTrail)) {
                        println("Trilha cadastrada com sucesso!")
                    } else {
                        println("Erro: Já existe uma trilha com esse ID.")
                    }
                }
                3 -> {
                    println("\n--- VINCULAR CURSO A TRILHA ---")
                    print("Código do Curso: ")
                    val code = scanner.nextLine()
                    print("ID da Trilha: ")
                    val id = scanner.nextInt()

                    if (catalog.addCourseToTrail(code, id)) {
                        println("Curso associado à trilha com sucesso!")
                    } else {
                        println("Erro: Verifique se o código do curso e o ID da trilha existem.")
                    }
                }
                4 -> {
                    println("\n--- TODOS OS CURSOS CADASTRADOS ---")
                    val courses = catalog.getAllCourses()
                    if (courses.isEmpty()) println("Nenhum curso cadastrado.")
                    courses.forEach { println("[${it.code}] ${it.title} - ${it.durationHours}h [${it.level}]") }
                }
                5 -> {
                    println("\n--- TODAS AS TRILHAS CADASTRADAS ---")
                    val trails = catalog.getAllTrails()
                    if (trails.isEmpty()) {
                        println("Nenhuma trilha cadastrada.")
                    } else {
                        trails.forEach { trail ->
                            println("\nTrilha ${trail.id}: ${trail.name} (${trail.totalDurationHours}h totais)")
                            println("Cursos inclusos nesta trilha:")
                            if (trail.courses.isEmpty()) println("  -> Nenhum curso vinculado.")
                            trail.courses.forEach { println("  -> [${it.code}] ${it.title}") }
                        }
                    }
                }
                6 -> {
                    println("\n--- FILTRAR CURSOS POR NÍVEL ---")
                    println("Escolha o nível (1 - BEGINNER, 2 - INTERMEDIATE, 3 - ADVANCED): ")
                    val choice = scanner.nextInt()
                    val targetLevel = when(choice) {
                        1 -> `CourseLevel(atv01)`.BEGINNER
                        2 -> `CourseLevel(atv01)`.INTERMEDIATE
                        else -> `CourseLevel(atv01)`.ADVANCED
                    }
                    val filtered = catalog.getCoursesByLevel(targetLevel)
                    println("\nCursos encontrados para o nível $targetLevel:")
                    if (filtered.isEmpty()) println("Nenhum curso neste nível.")
                    filtered.forEach { println(" - [${it.code}] ${it.title}") }
                }
                7 -> println("Saindo do sistema... Até logo!")
                else -> println("Opção inválida! Tente novamente.")
            }
        } catch (e: Exception) {
            println("Erro de entrada. Certifique-se de digitar números onde for pedido.")
            scanner.nextLine()
        }

    } while (option != 7)
}