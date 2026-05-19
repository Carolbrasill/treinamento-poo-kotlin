# Treinamento WorldSkills.
## Semana 02: Programação Orientada a Objetos Básica em Kotlin no Console

Este repositório foi estruturado para consolidar os fundamentos de Programação Orientada a Objetos (POO) utilizando a linguagem Kotlin no ambiente de console, estabelecendo a base arquitetural necessária para competições de alto nível (padrão WorldSkills).

Abaixo encontram-se as justificativas de arquitetura, decisões técnicas e respostas fundamentadas do **Bloco 1: Exercícios Conceituais** da apostila de treinamento, seguidas pelos códigos-fonte completos do sistema de catálogo desenvolvido e testado no IntelliJ IDEA.

---

## 🧠 Bloco 1: Respostas dos Exercícios Conceituais (Documentação Técnica)

### 1. Classe, Objeto e Referência
* **Conceito:** * **Classe:** É o "molde", contrato ou blueprint que define as propriedades (dados) e os comportamentos (funções/métodos) que os objetos daquela categoria possuirão. Ela reside estaticamente no código-fonte.
  * **Objeto:** É a instância concreta e dinâmica da classe criada em tempo de execução. Ele ocupa um espaço real na memória Heap do sistema e possui o seu próprio estado (valores das propriedades).
  * **Referência:** É a variável ou ponteiro que armazena o endereço de memória onde o objeto foi alocado. Ela permite interagir com o objeto.
* **Comportamento em Memória:**
  Se duas variáveis de referência apontam para o mesmo endereço de memória de um objeto mutável, qualquer alteração feita por meio de uma das referências será imediatamente visível através da outra, pois o objeto na memória é único.

### 2. Class Comum vs Data Class
* **Data Class:** Deve ser utilizada obrigatoriamente quando o propósito principal da classe for apenas atuar como um contentor estruturado de dados imutáveis (DTOs, modelos de domínio puros). O compilador do Kotlin gera automaticamente métodos utilitários essenciais em segundo plano: `equals()`, `hashCode()`, `toString()`, `componentN()` e o crucial método `copy()`.
* **Class Comum:** Deve ser escolhida quando a entidade possuir comportamento complexo, regras de negócio internas estritas, controle de estado mutável ou quando o encapsulamento precisar de blindar propriedades internas de modificações diretas.

### 3. Enum Class e As Suas Vantagens Estritas
* **Definição:** Uma `enum class` define um tipo que possui um conjunto fixo, fechado e imutável de valores constantes (ex: níveis de curso, status de matrícula).
* **Vantagens na Competição:**
  1. **Segurança de Tipos (Type Safety):** Elimina o uso de Strings "mágicas", prevenindo erros de digitação.
  2. **Exaustividade com o `when`:** O compilador do Kotlin obriga o competidor a tratar todos os cenários possíveis expostos no Enum, evitando falhas de lógica ocultas.

### 4. O Verdadeiro Propósito do Encapsulamento
* **Objetivo Real:** Garantir a integridade do estado interno de um objeto, impedindo que agentes externos insiram dados inválidos ou corrompam a lógica de negócios da aplicação.
* **Blindagem de Listas Mutáveis:**
  Para evitar que classes externas usem funções destrutivas diretamente em uma lista, a lista real deve ser declarada como `private val` interna. Para visualização externa, expõe-se uma cópia apenas de leitura (`List` imutável) por meio de uma propriedade calculada ou utilizando a função `.toList()`.

### 5. Null Safety e os Riscos Ocultos do Operador `!!`
* **O Perigo do `!!` (Not-Null Assertion):** Força o compilador a ignorar a segurança contra nulos. Caso a variável esteja nula na execução, o sistema disparará imediatamente um `NullPointerException (NPE)`, derrubando o aplicativo (Crash). Na WorldSkills, crashes eliminam muitos pontos.
* **Alternativas Profissionais:**
  1. **Chamada Segura (`?.`):** Executa se o objeto não for nulo.
  2. **Operador Elvis (`?:`):** Fornece um valor padrão caso o resultado seja nulo.

### 6. Modelagem de Domínio através de Requisitos (Briefing)
* **Metodologia de Extração:**
  Análise gramatical do briefing de prova:
  * **Substantivos:** Tornam-se as **Entidades** e **Modelos de Dados** (ex: Aluno, Curso, Trilha).
  * **Verbos:** Mapeiam os **Comportamentos** e **Regras de Negócio** (ex: matricular, calcular, ordenar).

### 7. Princípio da Responsabilidade Única (SRP) aplicada a POO
* **Conceito:** Cada classe deve possuir uma única razão para mudar. A classe `Student` ou `Course` deve conter apenas os seus dados cadastrais. A leitura de dados do console, processamento de relatórios e menus interativos devem ser isolados em classes de serviço distintas.

### 8. Padrão de Código Limpo (Clean Code) para Competições
1. **Imutabilidade por Padrão:** Dar preferência estrita ao uso de `val` em detrimento de `var`.
2. **Fail-Fast (Falha Rápida):** Utilizar blocos `init { require(...) }` para impedir instâncias inválidas na memória.
3. **Nomenclatura Semântica:** Utilizar termos precisos em inglês técnico (PascalCase para classes, camelCase para métodos/variáveis).

---

## 💻 Bloco 2 & 3: Códigos-Fonte do Sistema de Catálogo (Kotlin)

Abaixo estão consolidados todos os arquivos criados e validados no ambiente de desenvolvimento:

### 1. Nível de Curso (CourseLevel.kt)
```kotlin
enum class CourseLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}
data class Course(
    val code: String,
    val title: String,
    val durationHours: Int,
    val level: CourseLevel
) {
    init {
        require(code.isNotBlank()) { "O código do curso não pode estar em branco." }
        require(title.isNotBlank()) { "O título do curso não pode estar em branco." }
        require(durationHours > 0) { "A duração em horas deve ser maior que zero." }
    }
}
class Trail(
    val id: Int,
    val name: String
) {
    private val _courses = mutableListOf<Course>()
    val courses: List<Course> get() = _courses.toList()

    val totalDurationHours: Int
        get() = _courses.sumOf { it.durationHours }

    init {
        require(id > 0) { "O ID da trilha deve ser maior que zero." }
        require(name.isNotBlank()) { "O nome da trilha não pode estar em branco." }
    }

    fun addCourse(course: Course): Boolean {
        if (_courses.any { it.code == course.code }) return false
        return _courses.add(course)
    }

    fun removeCourse(courseCode: String): Boolean {
        return _courses.removeIf { it.code == courseCode }
    }
}
class CourseCatalog {
    private val courses = mutableListOf<Course>()
    private val trails = mutableListOf<Trail>()

    fun registerCourse(course: Course): Boolean {
        if (courses.any { it.code.equals(course.code, ignoreCase = true) }) return false
        return courses.add(course)
    }

    fun registerTrail(trail: Trail): Boolean {
        if (trails.any { it.id == trail.id }) return false
        return trails.add(trail)
    }

    fun addCourseToTrail(courseCode: String, trailId: Int): Boolean {
        val course = findCourseByCode(courseCode) ?: return false
        val trail = findTrailById(trailId) ?: return false
        return trail.addCourse(course)
    }

    fun getAllCourses(): List<Course> = courses.toList()
    fun getAllTrails(): List<Trail> = trails.toList()

    fun findCourseByCode(code: String): Course? = 
        courses.find { it.code.equals(code, ignoreCase = true) }

    fun findTrailById(id: Int): Trail? = 
        trails.find { it.id == id }

    fun getCoursesByLevel(level: CourseLevel): List<Course> {
        return courses.filter { it.level == level }
    }
}
import java.util.Scanner

fun main() {
    val catalog = CourseCatalog()
    val scanner = Scanner(System.`in`)
    var option = 0

    // Carga inicial de dados para testes
    val cursoBase = Course("KOT-101", "Kotlin Mobile", 40, CourseLevel.BEGINNER)
    catalog.registerCourse(cursoBase)
    val trilhaBase = Trail(1, "Formação Android")
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
                        1 -> CourseLevel.BEGINNER
                        2 -> CourseLevel.INTERMEDIATE
                        else -> CourseLevel.ADVANCED
                    }

                    val newCourse = Course(code, title, hours, level)
                    if (catalog.registerCourse(newCourse)) {
                        println("🎉 Curso cadastrado com sucesso!")
                    } else {
                        println("❌ Erro: Já existe um curso com esse código.")
                    }
                }
                2 -> {
                    println("\n--- CADASTRAR NOVA TRILHA ---")
                    print("ID numérico da Trilha: ")
                    val id = scanner.nextInt()
                    scanner.nextLine()
                    print("Nome da Trilha (ex: Formação iOS): ")
                    val name = scanner.nextLine()

                    val newTrail = Trail(id, name)
                    if (catalog.registerTrail(newTrail)) {
                        println("🎉 Trilha cadastrada com sucesso!")
                    } else {
                        println("❌ Erro: Já existe uma trilha com esse ID.")
                    }
                }
                3 -> {
                    println("\n--- VINCULAR CURSO A TRILHA ---")
                    print("Código do Curso: ")
                    val code = scanner.nextLine()
                    print("ID da Trilha: ")
                    val id = scanner.nextInt()

                    if (catalog.addCourseToTrail(code, id)) {
                        println("🎉 Curso associado à trilha com sucesso!")
                    } else {
                        println("❌ Erro: Verifique os dados informados.")
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
                        1 -> CourseLevel.BEGINNER
                        2 -> CourseLevel.INTERMEDIATE
                        else -> CourseLevel.ADVANCED
                    }
                    val filtered = catalog.getCoursesByLevel(targetLevel)
                    println("\nCursos encontrados para o nível $targetLevel:")
                    if (filtered.isEmpty()) println("Nenhum curso neste nível.")
                    filtered.forEach { println(" - [${it.code}] ${it.title}") }
                }
                7 -> println("Saindo do sistema... Até logo!")
                else -> println("❌ Opção inválida!")
            }
        } catch (e: Exception) {
            println("❌ Erro de entrada de dados.")
            scanner.nextLine() 
        }
    } while (option != 7)
}
