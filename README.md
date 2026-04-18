# MyFood — Sistema de Delivery

Projeto da disciplina de Programação 2 (UFAL). Sistema de delivery inspirado no iFood, implementado em Java puro sem interface gráfica. A avaliação é feita exclusivamente via testes de aceitação com EasyAccept.

---

## Como rodar

> O código está dentro da pasta `MyFood2026.1/`. O IntelliJ (ou qualquer IDE) precisa abrir essa pasta como raiz do projeto, não a pasta externa.

**Pré-requisitos:**
- JDK 11 ou superior
- IntelliJ IDEA (recomendado) ou outra IDE Java

**Passos:**
1. Abra o projeto apontando para `MyFood2026.1/` (onde está o `src/`)
2. Certifique-se que `MyFood2026.1/lib/easyaccept.jar` está no classpath
3. Em `Main.java`, descomente as linhas dos testes que quiser rodar
4. Execute a classe `br.ufal.ic.myfood.Main`

Os arquivos de dados XML são gerados automaticamente em `dados/` na raiz do projeto quando o sistema é encerrado (`encerrarSistema`).

**Atenção:** os testes de aceitação referenciam os arquivos de teste com o caminho `MyFood2026.1/tests/usX_Y.txt`. Por isso o working directory ao rodar deve ser a pasta raiz (`MyFood2026.1.1/`), não a subpasta.

---

## Estrutura de pastas

```
MyFood2026.1/               <- raiz do repositório
├── README.md
├── docs/                   <- documentação de cada US
│   ├── DOC_GERAL_US1.md
│   ├── DOC_GERAL_US2.md
│   ├── DOC_GERAL_US3.md
│   └── DOC_GERAL_US4.md
├── dados/                  <- arquivos XML gerados em tempo de execução
│   ├── usuarios.xml
│   ├── empresas.xml
│   ├── produtos.xml
│   └── pedidos.xml
└── MyFood2026.1/           <- projeto Java
    ├── lib/
    │   └── easyaccept.jar
    ├── tests/              <- arquivos de teste EasyAccept
    │   ├── us1_1.txt  us1_2.txt
    │   ├── us2_1.txt  us2_2.txt
    │   ├── us3_1.txt  us3_2.txt
    │   └── us4_1.txt  us4_2.txt
    └── src/br/ufal/ic/myfood/
        ├── Facade.java             <- único ponto de entrada do EasyAccept
        ├── Main.java               <- runner dos testes
        ├── models/                 <- entidades do domínio
        │   ├── Usuario.java        <- classe abstrata base
        │   ├── Cliente.java
        │   ├── DonoDeEmpresa.java
        │   ├── Empresa.java        <- classe abstrata base
        │   ├── Restaurante.java
        │   ├── Produto.java
        │   └── Pedido.java
        ├── repositories/           <- persistência XML
        │   ├── UsuarioRepository.java
        │   ├── EmpresaRepository.java
        │   ├── ProdutoRepository.java
        │   └── PedidoRepository.java
        ├── services/               <- regras de negócio
        │   ├── UsuarioService.java
        │   ├── EmpresaService.java
        │   ├── ProdutoService.java
        │   └── PedidoService.java
        └── exceptions/             <- uma classe por tipo de erro (25 no total)
```

---

## Arquitetura

O sistema é dividido em quatro camadas bem definidas:

### Facade
Único ponto de entrada acessado pelo EasyAccept. Não contém lógica — apenas recebe as chamadas dos testes e delega para os Services. Isso isola completamente a interface de teste da lógica de negócio.

### Services
Onde ficam as regras de negócio: validações, ordem de verificação, restrições entre entidades. Cada entidade principal tem seu próprio Service. Os Services se comunicam entre si quando necessário — por exemplo, o `PedidoService` consulta o `ProdutoService` para calcular o valor total de um pedido.

### Repositories
Responsáveis exclusivamente pela persistência em XML. Cada Repository gerencia o arquivo XML da sua entidade, o contador de IDs e as operações de busca (por ID, por nome, por combinação de campos). Não conhecem regras de negócio.

### Models
Entidades puras do domínio. Só têm campos, construtores e getters/setters. São JavaBeans para funcionar com `XMLEncoder`/`XMLDecoder`.

### Fluxo de uma chamada

```
EasyAccept -> Facade -> Service -> Repository -> XML
                           |
                    (consulta outros Services quando necessário)
```

---

## Design do sistema

A separação entre Repository e Service foi uma decisão deliberada para respeitar o **Single Responsibility Principle**: um objeto não deve ser responsável por aplicar regras de negócio e ao mesmo tempo saber como persistir dados. Antes dessa separação, havia classes "Manager" que misturavam as duas coisas, o que tornava difícil entender onde estava cada responsabilidade.

Com a separação atual, trocar o mecanismo de persistência (por exemplo, de XML para banco de dados) exigiria alterar apenas os Repositories, sem tocar nas regras de negócio nos Services.

---

## Conceitos de POO aplicados

### Herança
As entidades do domínio usam hierarquia de herança onde faz sentido:

- `Usuario` (abstrata) → `Cliente` / `DonoDeEmpresa`
- `Empresa` (abstrata) → `Restaurante`

Cada subclasse implementa o método abstrato `getTipo()`, que retorna uma string identificando o tipo. Isso permite, por exemplo, que o sistema identifique se um usuário pode criar empresas sem depender de verificações externas ao objeto.

### Encapsulamento
Todos os campos das entidades são `private`. O acesso é feito exclusivamente via getters e setters públicos. As regras de negócio ficam nos Services, não expostas diretamente nas entidades.

### Abstração
As classes `Usuario` e `Empresa` definem o contrato comum a todas as variações. Métodos como `getTipo()` são abstratos, forçando cada subclasse a declarar explicitamente sua natureza.

### Polimorfismo
Usado nos Services quando é necessário acessar atributos específicos de uma subclasse. Por exemplo, `getAtributoEmpresa` com atributo `tipoCozinha` usa `instanceof Restaurante` para acessar o campo que só existe no restaurante.

---

## Padrões de projeto

### Facade
**Descrição:** Define uma interface simplificada para um conjunto de interfaces em um subsistema.

**Problema resolvido:** O EasyAccept precisa de um único ponto de entrada com métodos que correspondam exatamente aos comandos dos scripts de teste. Sem a Facade, o EasyAccept teria que conhecer a estrutura interna do sistema.

**Aplicação:** A classe `Facade.java` expõe todos os comandos do sistema (criarUsuario, login, criarPedido, etc.) e delega cada um para o Service correspondente. Os testes nunca sabem que existem Services ou Repositories.

---

### Repository
**Descrição:** Separa a lógica de acesso a dados da lógica de negócio, fornecendo uma interface de coleção para trabalhar com objetos de domínio.

**Problema resolvido:** Antes da separação, as classes Manager misturavam validações de negócio com operações de XML. Qualquer mudança na forma de persistir dados exigia mexer no mesmo lugar onde estavam as regras.

**Identificação da oportunidade:** Ficou claro que métodos como `salvarDados()` e `carregarDados()` não têm relação com validar se um produto tem nome duplicado. Eram responsabilidades distintas na mesma classe.

**Aplicação:** Cada entidade tem seu Repository (`UsuarioRepository`, `EmpresaRepository`, `ProdutoRepository`, `PedidoRepository`). O Repository cuida de: salvar/carregar o XML, gerar IDs sequenciais e buscar por diferentes critérios. O Service usa o Repository sem saber como os dados são armazenados.

---

## User Stories implementadas (Milestone 1)

| US | Titulo | Status |
|---|---|---|
| US1 | Criação de contas (Cliente e Dono de Empresa) | Completo |
| US2 | Criação de Restaurantes | Completo |
| US3 | Criação de Produtos | Completo |
| US4 | Criação de Pedidos | Completo |

---

## Persistência

Os dados são salvos em XML usando `java.beans.XMLEncoder` e carregados com `java.beans.XMLDecoder`. Cada entidade tem seu próprio arquivo em `dados/`:

| Arquivo | Conteúdo |
|---|---|
| `dados/usuarios.xml` | Lista de usuários + próximo ID |
| `dados/empresas.xml` | Lista de empresas + próximo ID |
| `dados/produtos.xml` | Lista de produtos + próximo ID |
| `dados/pedidos.xml` | Lista de pedidos + próximo número |

Os dados são carregados no construtor da Facade (ao iniciar) e salvos no `encerrarSistema()`. O `zerarSistema()` apaga tudo da memória sem sobrescrever os arquivos — os arquivos só são atualizados no próximo `encerrarSistema()`.
