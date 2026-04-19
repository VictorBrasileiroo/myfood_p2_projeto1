# MyFood — Sistema de Delivery

Nome: Victor André Lopes Brasileiro
Matrícula: 202407269

Projeto da disciplina de Programação 2 (UFAL/IC). Sistema de delivery inspirado no iFood, desenvolvido em Java puro sem interface gráfica. A lógica de negócio é verificada por testes de aceitação via EasyAccept.

---

## Como rodar

O código-fonte fica dentro da subpasta `MyFood2026.1/`. Ao abrir na IDE, aponte para essa pasta como raiz do projeto (onde está o `src/`), não para a pasta externa.

**Requisitos:**
- JDK 11 ou superior
- IntelliJ IDEA ou outra IDE Java com suporte a projetos sem build tool

**Passos:**
1. Abra o projeto apontando para `MyFood2026.1/` (onde está o `src/`)
2. Adicione `MyFood2026.1/lib/easyaccept.jar` ao classpath do projeto
3. Em `Main.java`, descomente as linhas dos testes que quiser executar
4. Rode a classe `br.ufal.ic.myfood.Main`

Os arquivos XML de persistência são criados automaticamente na pasta `dados/` quando `encerrarSistema` é chamado ao final dos testes.

> Atenção: os testes referenciam os arquivos com o caminho `MyFood2026.1/tests/usX_Y.txt`, então o working directory precisa ser a pasta raiz do repositório, não a subpasta.

---

## Estrutura de pastas

```
MyFood2026.1/                      <- raiz do repositório
├── README.md
├── docs/                          <- documentação por User Story
│   ├── DOC_GERAL_US1.md
│   ├── DOC_GERAL_US2.md
│   ├── DOC_GERAL_US3.md
│   └── DOC_GERAL_US4.md
└── MyFood2026.1/                  <- projeto Java
    ├── lib/
    │   └── easyaccept.jar
    ├── tests/                     <- scripts de teste EasyAccept
    │   ├── us1_1.txt  us1_2.txt
    │   ├── us2_1.txt  us2_2.txt
    │   ├── us3_1.txt  us3_2.txt
    │   └── us4_1.txt  us4_2.txt
    └── src/br/ufal/ic/myfood/
        ├── Facade.java            <- ponto de entrada dos testes
        ├── Main.java              <- runner
        ├── models/                <- entidades do domínio
        ├── repositories/          <- persistência XML
        ├── services/              <- regras de negócio
        └── exceptions/            <- exceções por tipo de erro
```

---

## Design arquitetural do sistema

O sistema é organizado em quatro camadas. A ideia central é que cada camada tem uma responsabilidade bem delimitada e não mistura coisas que não têm a ver uma com a outra.

**Facade** — é o único arquivo que o EasyAccept acessa. Não tem lógica nenhuma: só recebe o comando e passa para quem sabe resolver. Isso mantém a separação entre a interface de testes e o resto do sistema.

**Services** — ficam as regras de negócio. É aqui que se valida se o nome está vazio, se o email já existe, se o cliente é dono de empresa, se já tem pedido aberto. Cada entidade principal tem seu Service, e eles se comunicam entre si quando necessário (por exemplo, o `PedidoService` usa o `ProdutoService` para calcular o valor total).

**Repositories** — cuidam só de salvar e carregar dados em XML. Não sabem nada de regra de negócio. Cada Repository gerencia seu arquivo XML, o contador de IDs e as buscas simples (por ID, por email, por combinação de campos).

**Models** — as entidades do domínio: `Usuario`, `Empresa`, `Produto`, `Pedido`. São JavaBeans puros, com campos privados e getters/setters, necessários para o `XMLEncoder` serializar corretamente.

**Fluxo de uma chamada:**

```
EasyAccept → Facade → Service → Repository → arquivo XML
                         ↕
              (Services conversam entre si quando precisam)
```

---

## Principais componentes e suas interações

| Componente | Responsabilidade | Depende de |
|---|---|---|
| `Facade` | Receber e repassar comandos | Services |
| `UsuarioService` | Criar usuário, login, validações | `UsuarioRepository` |
| `EmpresaService` | Criar empresa, consultar atributos | `EmpresaRepository`, `UsuarioService` |
| `ProdutoService` | Criar/editar produto, listar | `ProdutoRepository`, `EmpresaService` |
| `PedidoService` | Criar pedido, adicionar/remover produtos, fechar | `PedidoRepository`, `UsuarioService`, `EmpresaService`, `ProdutoService` |
| Repositories | Persistência XML e busca de dados | Arquivos XML em `dados/` |
| Models | Representar os dados do domínio | — |

---

## Conceitos de POO aplicados

**Herança:** `Usuario` e `Empresa` são classes abstratas. `Cliente` e `DonoDeEmpresa` estendem `Usuario`; `Restaurante` estende `Empresa`. Cada subclasse implementa `getTipo()`, que identifica o tipo sem precisar de verificação externa.

**Encapsulamento:** todos os campos são `private`, acessados via getters e setters. As regras ficam nos Services, não espalhadas pelas entidades.

**Abstração:** as classes base definem o contrato — `getTipo()` é abstrato, então toda subclasse é obrigada a implementar. Isso evita que o sistema opere com objetos indefinidos.

**Polimorfismo:** nos Services, quando um atributo só existe em uma subclasse (como `tipoCozinha` no `Restaurante`), é feito o `instanceof` para acessá-lo. O resto do código trata tudo como `Empresa` ou `Usuario`.

---

## Padrões de projeto

### Padrão Facade

**Descrição Geral**

O Facade é um padrão estrutural que cria uma interface simplificada para um conjunto de classes internas de um sistema. Em vez de o cliente precisar conhecer como as partes internas funcionam, ele faz tudo por meio de uma única classe que organiza e repassa as chamadas.

**Problema Resolvido**

Em sistemas com várias camadas, quem chama de fora não deveria precisar saber como o sistema está organizado internamente. O problema é quando a classe de entrada começa a concentrar lógica, misturando responsabilidades que não são dela.

**Identificação da Oportunidade**

O EasyAccept exige um único ponto de entrada com métodos que correspondam exatamente aos comandos dos scripts de teste. Qualquer lógica dentro da `Facade` tornaria os testes dependentes da implementação. Ficou claro que a `Facade` deveria ser só um repasse, e toda a lógica precisava ficar em outro lugar.

**Aplicação no Projeto**

A classe `Facade.java` contém todos os métodos chamados pelo EasyAccept (`criarUsuario`, `login`, `criarEmpresa`, `criarProduto`, `criarPedido`, etc.). Cada método apenas delega para o Service responsável:

```java
public int criarPedido(int cliente, int empresa) throws Exception {
    return pedidoService.criarPedido(cliente, empresa);
}
```

Os testes não sabem que existem Services, Repositories ou Models. Toda mudança interna no sistema pode ser feita sem alterar a Facade, e os testes continuam funcionando.

---

### Padrão Repository

**Descrição Geral**

O Repository é um padrão que isola o acesso a dados do resto da aplicação. Ele age como uma coleção de objetos de domínio, escondendo os detalhes de como os dados são salvos e recuperados. O código que usa um Repository não precisa saber se os dados estão em XML, banco de dados ou qualquer outro lugar.

**Problema Resolvido**

O padrão resolve o problema de classes que acumulam responsabilidades demais: aplicar regras de negócio e ao mesmo tempo saber como persistir dados. Quando essas duas coisas ficam juntas, uma mudança pequena na forma de salvar pode quebrar uma validação, e fica difícil testar as regras de negócio isoladas.

**Identificação da Oportunidade**

No início do projeto, as classes chamadas `Manager` faziam as duas coisas ao mesmo tempo: validavam campos, aplicavam regras de criação, e também tinham os métodos `salvarDados()` e `carregarDados()` com o XML. Ficou nítido que `salvarDados()` não tem nada a ver com "o nome do produto não pode ser duplicado na mesma empresa". Eram dois problemas diferentes vivendo na mesma classe.

**Aplicação no Projeto**

Cada entidade tem seu Repository: `UsuarioRepository`, `EmpresaRepository`, `ProdutoRepository` e `PedidoRepository`. O Repository cuida de três coisas: salvar e carregar o XML, gerar IDs sequenciais, e buscar por critérios simples (por ID, por email, por nome+empresa).

```java
// No UsuarioRepository — só persistência e busca
public void salvarDados() throws IOException { ... }
public Usuario buscarPorId(int id) throws UsuarioNaoExisteException { ... }
public boolean existeComEmail(String email) { ... }

// No UsuarioService — só regra de negócio
public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
    validarNome(nome);
    validarEmail(email);
    validarEmailDuplicado(email); // usa repository.existeComEmail(email)
    repository.salvar(new Cliente(repository.gerarId(), nome, email, senha, endereco));
}
```

O Service usa o Repository sem saber se os dados estão em XML ou em outro lugar. Isso mantém as regras de negócio testáveis e a persistência trocável.

---

## User Stories implementadas — Milestone 1

| US | Título | Testes |
|---|---|---|
| US1 | Criação de contas | us1_1, us1_2 |
| US2 | Criação de Restaurantes | us2_1, us2_2 |
| US3 | Criação de Produtos | us3_1, us3_2 |
| US4 | Criação de Pedidos | us4_1, us4_2 |

---

## Persistência

Os dados são salvos em XML com `java.beans.XMLEncoder` e carregados com `java.beans.XMLDecoder`. Cada entidade tem seu arquivo:

| Arquivo | Conteúdo |
|---|---|
| `dados/usuarios.xml` | lista de usuários + próximo ID |
| `dados/empresas.xml` | lista de empresas + próximo ID |
| `dados/produtos.xml` | lista de produtos + próximo ID |
| `dados/pedidos.xml` | lista de pedidos + próximo número |

Os arquivos são criados/atualizados só quando `encerrarSistema` é chamado. O `zerarSistema` limpa tudo da memória mas não mexe nos arquivos — eles só mudam no próximo `encerrarSistema`.
