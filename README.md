Esta API foi desenvolvida como projeto de estudos, implementando uma biblioteca digital com autenticação JWT e controle de acesso baseado em roles. O sistema permite o gerenciamento de usuários, autores e livros, com diferentes níveis de permissão.

🛠 Tecnologias Utilizadas
Java (versão 17 ou superior)

Spring Boot (3.x)

Spring Data JPA

MySQL (banco de dados)

Spring Security

JWT (JSON Web Tokens)

Maven (gerenciamento de dependências)

Lombok (para redução de boilerplate code)

🔐 Funcionalidades de Autenticação
Cadastro de usuários com diferentes roles:

GERENTE - Acesso total ao sistema

OPERADOR - Pode gerenciar livros e autores

CLIENTE - Apenas visualização

Login com geração de token JWT

Validação customizada de tokens JWT

Controle de acesso baseado em roles para endpoints

📖 Funcionalidades da Biblioteca
CRUD de Autores

CRUD de Livros (apenas para roles autorizados)

Associação entre livros e autores

Buscas por:

Título do livro

Nome do autor

Gênero literário

Ano de publicação
