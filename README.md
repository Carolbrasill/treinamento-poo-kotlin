# Treinamento WorldSkills 

<p align="center">
  <img src="https://raw.githubusercontent.com/worldskills/worldskills.github.io/master/assets/images/logo.png" alt="WorldSkills Logo" width="220"/>
</p>

## Semana 02: Programação Orientada a Objetos Básica em Kotlin
Este repositório contém a resolução dividida e estruturada de todas as **20 atividades práticas e conceituais** da apostila de treinamento de POO.

## 📁 Estrutura do Projeto (Arquivos Separados por Atividade)
Os códigos-fonte foram componentizados e distribuídos em arquivos individuais dentro do diretório principal para garantir o Princípio da Responsabilidade Única (SRP):

* `CourseLevel.kt` -> Definição dos níveis do catálogo (Atividade 1).
* `Course.kt` -> Modelo de dados do Curso com validações fail-fast (Atividades 2, 3 e 4).
* `Trail.kt` -> Gerenciamento de cursos e cálculo de carga horária (Atividades 5, 6, 7 e 8).
* `CourseCatalog.kt` -> Sistema de busca, filtros avançados e persistência em memória (Atividades 9 a 15).
* `Main.kt` -> Fluxo interativo e interface de console para o usuário (Atividades 16 a 20).

---

## 🧠 Bloco 1: Respostas dos Exercícios Conceituais

### 1. Classe, Objeto e Referência
* **Classe:** É o blueprint/molde estático que define propriedades e comportamentos.
* **Objeto:** É a instância física alocada na memória Heap durante a execução.
* **Referência:** É o ponteiro que armazena o endereço de memória do objeto. Se duas referências apontam para o mesmo objeto mutável, alterações feitas por uma alteram o que a outra enxerga.

### 2. Class Comum vs Data Class
* **Data Class:** Focada estritamente em armazenar dados imutáveis. O compilador gera automaticamente métodos como `equals()`, `hashCode()`, `toString()` e `copy()`.
* **Class Comum:** Utilizada quando a entidade possui comportamento complexo, lógica de negócios interna ou necessidade de encapsulamento rígido.

### 3. Enum Class e As Suas Vantagens Estritas
* **Type Safety:** Elimina strings mágicas, prevenindo erros de digitação durante a prova.
* **Exaustividade:** Obriga o tratamento de todos os cenários possíveis em blocos `when`.

### 4. O Verdadeiro Propósito do Encapsulamento
* **Integridade:** Proteger o estado interno contra corrupção de dados por agentes externos.
* **Blindagem:** Expor listas mutáveis internas como cópias apenas de leitura (`List` imutável) via `.toList()`.

### 5. Null Safety e os Riscos Ocultos do Operador `!!`
* O operador `!!` força a asserção de não-nulo. Se o valor for nulo, causa um `NullPointerException (NPE)` e derruba a aplicação (Crash), gerando desclassificação ou perda massiva de pontos. Deve ser substituído por chamadas seguras (`?.`) e o operador Elvis (`?:`).

### 6. Modelagem de Domínio através de Requisitos (Briefing)
* Substantivos no briefing tornam-se classes/propriedades; verbos tornam-se métodos/regras de negócio.

### 7. Princípio da Responsabilidade Única (SRP) aplicada a POO
* Uma classe deve ter apenas um motivo para mudar. Classes de dados não devem gerenciar menus de console ou inputs de usuário.

### 8. Padrão de Código Limpo (Clean Code) para Competições
* Uso de `val` por padrão, validações em blocos `init` com `require`, e nomenclatura semântica em inglês técnico.
