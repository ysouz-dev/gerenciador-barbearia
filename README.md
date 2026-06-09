# 💈 Sistema de Gerenciamento de Barbearia

Este sistema simula o funcionamento de uma barbearia, permitindo o cadastro de clientes, registro de atendimentos e controle dos serviços realizados.

O foco principal do projeto foi aplicar na prática conceitos fundamentais de backend utilizando Java.

## ⚙️ Funcionalidades

* Cadastro de clientes
* Registro de atendimentos
* Associação de serviços a cada atendimento
* Cálculo automático do valor total
* Listagem de clientes e atendimentos
* Remoção de registros
* Exibição de estatísticas do sistema

## 🧠 Conceitos aplicados

* Programação Orientada a Objetos (POO)
* Encapsulamento
* Herança
* Polimorfismo
* Interfaces
* Enum
* Estruturas de dados (ArrayList, List, Map, HashMap)
* Tratamento de exceções
* Organização em camadas (model, service, repository ,controller, util)
* JDBC - Conexão com banco de dados MySQL (PreparedStatement, ResultSet)

## 🛠️ Tecnologias utilizadas

* Java 21
* Paradigma Orientado a Objetos
* Maven
* MySQL
* JDBC (MySQL Connector / J)

## 🎲 Configuração do banco de dados
**1. Execute o script de criação das tabelas:**
```
mysql -u root -p < database/schema.sql
```

**2. Execute o script de dados iniciais:**
```
mysql -u root -p < database/seed.sql
```

**3. Configure as credencias de conexão**

Crie o arquivo `src/main/resources/database.properties` baseado no exemplo:
```
Properties

database.url = jdbc:mysql://localhost:3306/seu_banco
database.user = seu_usuario
data.pass = sua_senha
```

> O arquivo `database.properties` está no `gitignore` e não é versionado por conter dados sensíveis.
> Mas disponibilizei um exemplo com `databaseExample.properties`

## 🧑‍💻 Como Rodar

**1. Clone o repositório**
``` 
git clone https://github.com/ysouz-dev/gerenciador-barbearia
```

**2. Configure o banco de dados conforme as instruções acima**

**3. Abra o projeto no IntelliJ IDEA como projeto Maven**

**4. Execute a classe `Main.JAVA`**

## 🚀 Objetivo

Este projeto foi desenvolvido como forma de consolidar o aprendizado em Java e evoluir na construção de sistemas mais organizados e próximos de cenários reais.

## 📚 Próximos passos

- [x] Implementar uso de HashMap para melhorar buscas
- [x] Persistência de dados (arquivo ou banco de dados)
- [ ] Teste unitários com JUnit
- [ ] Migrar para SpringBoot
