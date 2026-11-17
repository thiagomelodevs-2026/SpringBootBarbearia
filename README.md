# Barbearia

Aplicação web para uma barbearia, desenvolvida com Java, Spring Boot, Thymeleaf e Bootstrap. Inclui páginas para agendamento e administração, além de uma API REST para cadastro de barbeiros.

## Requisitos

- Java 21 ou superior
- Não é necessário instalar o Maven: o projeto inclui Maven Wrapper

## Como executar no Windows

Abra o PowerShell nesta pasta (`MyFirtsSpringBoot-main`) e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

Quando a aplicação iniciar, acesse <http://localhost:8080>. Para encerrar, pressione `Ctrl+C` no terminal.

Para executar os testes:

```powershell
.\mvnw.cmd test
```

## Páginas

| Endereço | Página |
| --- | --- |
| `/` ou `/agendar` | Página inicial |
| `/cadastro` | Cadastro de cliente |
| `/agendamento` | Formulário de agendamento |
| `/servicos` | Serviços da barbearia |
| `/equipe` | Equipe (barbeiros cadastrados) |
| `/trabalhe-conosco` | Trabalhe conosco |
| `/admin/login` | Login da administração |
| `/admin/painel-agenda` ou `/admin/painel` | Painel da agenda |
| `/cliente/status` | Consulta de status do agendamento |
| `/cliente/cancelar` | Cancelamento de agendamento |
| `/cliente/remarcar` | Remarcação de agendamento |

## API de barbeiros

A API base é `/api/barbeiros` e oferece operações para listar, consultar, criar, atualizar e excluir barbeiros.

| Método | Endereço | Ação |
| --- | --- | --- |
| `GET` | `/api/barbeiros` | Lista barbeiros |
| `GET` | `/api/barbeiros/{id}` | Consulta um barbeiro |
| `POST` | `/api/barbeiros` | Cria um barbeiro |
| `PUT` | `/api/barbeiros/{id}` | Atualiza um barbeiro |
| `DELETE` | `/api/barbeiros/{id}` | Exclui um barbeiro |

Exemplo de corpo para `POST` e `PUT`:

```json
{
  "nome": "João",
  "foto": "/images/joao.jpg",
  "senhaHash": "<hash-da-senha>",
  "tentativasLoginFalhas": 0,
  "bloqueadoAte": null
}
```

`bloqueadoAte` aceita uma data e hora no formato ISO 8601, por exemplo `"2026-10-20T21:30:00"`, ou `null`. A API não retorna o campo `senhaHash` nas respostas. Envie nesse campo somente um hash de senha, nunca uma senha em texto puro.

## Banco de dados

A aplicação usa PostgreSQL no Supabase. Copie `.env.exemple` para `.env` na raiz do projeto e preencha `DB_URL`, `DB_USER` e `DB_PASSWORD` com os valores de **Connect → Session pooler** no painel do Supabase. Use a URL JDBC (`jdbc:postgresql://...`) com `sslmode=require`; a aplicação importa esse arquivo ao iniciar. O `.env` é ignorado pelo Git e não deve ser compartilhado.

As tabelas são atualizadas automaticamente pelo JPA. O pool de conexões fica limitado a três conexões para respeitar os limites do plano Supabase.


## Desenvolvedores

- Thiago Melo — front-end e back-end; responsável pela empresa
- Sergio Henry — back-end; busca e números de agendamento
- Patricia Oliveira — back-end e front-end; responsável por vagas
- Kaue Apolinario — back-end; pessoa e login