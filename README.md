Alunos - Sistema de Gestão
=================================

Descrição
---------------------------------
Aplicação web em Spring Boot para cadastro e gestão de alunos, usando MVC com Thymeleaf, JPA/Hibernate e banco em memória H2. Permite listar, cadastrar, editar, visualizar e excluir alunos.

Stack
---------------------------------
- Spring Boot 3.3.x (Web, Thymeleaf, Data JPA, Validation)
- H2 Database (memória)
- Maven
- Java 17+

Requisitos
---------------------------------
- Java 17 ou superior (Spring Boot 3 requer Java 17+)
- Maven 3.9+

Como executar (modo desenvolvimento)
---------------------------------
1. Clonar o repositório e entrar no diretório do projeto
2. Rodar a aplicação:
   ```bash
   mvn spring-boot:run
   ```
3. Acessar a aplicação em `http://localhost:8080/alunos`
4. Console do H2 (opcional) em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:alunosdb`, usuário: `sa`, senha vazia)

Modelo de dados
---------------------------------
- Entidade `Aluno`: `id`, `nome` (obrigatório), `matricula` (somente dígitos, única), `curso` (obrigatório), `email` (válido, obrigatório)
- Regras: matrícula é única (validada na aplicação e por restrição no banco)

Rotas (MVC)
---------------------------------
Controlador base: `AlunoController` em `/alunos`.

- GET `/alunos`: lista alunos (view `listar`) com paginação (`?page=0&size=10`)
- GET `/alunos/novo`: formulário de cadastro (view `cadastrar`)
- POST `/alunos`: salva novo aluno (redirect para `/alunos`)
- GET `/alunos/editar/{id}`: carrega formulário de edição (view `editar`)
- POST `/alunos/editar/{id}`: atualiza aluno (redirect para `/alunos`)
- GET `/alunos/visualizar/{id}`: detalhes do aluno (view `visualizar`)
- DELETE `/alunos/excluir/{id}`: exclui aluno (redirect para `/alunos`)
  - As views enviam formulário `POST` com `_method=delete` para compatibilidade HTML

Templates
---------------------------------
Localizados em `src/main/resources/templates/`:
- `listar.html`, `cadastrar.html`, `editar.html`, `visualizar.html`
- Mensagens de feedback: `success` e `error` exibidas nas telas
- Validações de campo exibidas junto aos inputs; matrícula aceita apenas dígitos
- Listagem inclui paginação básica (anterior/próxima e numeração)

Estrutura de diretórios
---------------------------------
```
src/
  main/
    java/com/exemplo/alunos/
      AlunoSistemaApplication.java
      controller/AlunoController.java
      model/Aluno.java
      repository/AlunoRepository.java
      service/AlunoService.java
    resources/
      application.properties
      templates/ (views Thymeleaf)
      static/ (assets)
```

Notas de desenvolvimento
---------------------------------
- Banco H2 em memória: dados são descartados ao parar a aplicação.
