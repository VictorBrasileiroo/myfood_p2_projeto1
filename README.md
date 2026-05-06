# MyFood: Sistema de Delivery

Nome: Victor André Lopes Brasileiro

Matrícula: 202407269

Projeto da disciplina de Programação 2 (UFAL/IC). É um sistema de delivery tipo iFood, feito em Java puro sem interface gráfica. Os testes de aceitação rodam via EasyAccept.

---

## Como rodar

O código-fonte tá dentro da pasta MyFood2026.1/. Quando abrir na IDE, aponta pra essa pasta como raiz do projeto (onde tem o src/), não pra pasta de fora.

**Passos:**
1. Abra o projeto apontando para MyFood2026.1/ 
2. Adicione MyFood2026.1/lib/easyaccept.jar ao classpath do projeto
3. Rode a classe br.ufal.ic.myfood.Main

Os arquivos XML são criados automaticamente na pasta dados/ quando encerrarSistema é chamado ao final dos testes.

Os testes buscam os arquivos em MyFood2026.1/tests/usX_Y.txt, então se mudar a estrutura de pastas, tem que atualizar os caminhos nos testes.

---

## Arquitetura do sistema

O sistema é organizado em quatro camadas, cada uma com uma responsabilidade bem definida. A ideia é não misturar coisas que não têm nada a ver uma com a outra.

**Facade**: é o único arquivo que o EasyAccept fala. Não tem lógica nenhuma aqui, só recebe o comando e passa pra quem tem que resolver.

**Services**: é onde ficam as regras de negócio. Aqui a gente valida as regras de criação, edição, consulta, etc.

**Repositories**: só cuida de salvar e carregar dados em XML. Não tem conhecimento nenhum de regra de negócio.

**Models**: as entidades do sistema: Usuario, Empresa, Produto, Pedido e Entrega, além das especializações Cliente, DonoDeEmpresa, Entregador, Restaurante, Mercado e Farmacia.

---

## Principais componentes e suas interações

| Componente | Responsabilidade | Depende de |
|---|---|---|
| Facade | Receber e repassar comandos | Services |
| UsuarioService | Criar usuário, login, validações | UsuarioRepository |
| EmpresaService | Criar empresa, consultar atributos | EmpresaRepository, UsuarioService |
| ProdutoService | Criar/editar produto, listar | ProdutoRepository, EmpresaService |
| PedidoService | Criar pedido, adicionar/remover produtos, fechar | PedidoRepository, UsuarioService, EmpresaService, ProdutoService |
| EntregaService | Criar entrega, consultar entrega e marcar como entregue | EntregaRepository, PedidoService, UsuarioService, EmpresaService, ProdutoService |
| Repositories | Persistência XML e busca de dados | Arquivos XML em dados/ |
| Models | Representar os dados do domínio |  |

---

## Conceitos de POO aplicados

**Herança:** Usuario e Empresa são abstratas. Cliente, DonoDeEmpresa e Entregador herdam de Usuario; Restaurante, Mercado e Farmacia herdam de Empresa. Cada uma tem seu getTipo() pra identificar o tipo sem ficar fazendo if/else espalhado.

**Encapsulamento:** tudo é private, com getters e setters. As regras do negócio ficam nos Services, não espalhadas por aí.

**Abstração:** as classes base definem o contrato, getTipo() é abstrato, então toda subclasse tem que implementar. Isso evita trabalhar com objetos que não sabem o que são.

**Polimorfismo:** nos services, quando um atributo é só de uma subclasse, usa-se instanceof pra acessar. O resto do código trata restaurantes, mercados e farmacias como Empresa, e clientes, donos e entregadores como Usuario.

---

## Padrões de projeto

### Padrão Facade

**Descrição Geral**

O Facade é um padrão que cria uma interface simplificada pra um monte de classes internas. Em vez do cliente precisar saber como funciona internamente, ele faz tudo por uma única classe que repassa as chamadas.

**Problema Resolvido**

Em sistemas com várias camadas, quem chama de fora não deveria ficar sabendo como o sistema é organizado. O problema começa quando a classe de entrada fica concentrando lógica, misturando coisas que não são da responsabilidade dela.

**Identificação da Oportunidade**

O EasyAccept exige um único ponto de entrada com métodos que correspondam aos comandos dos testes. Se a Facade tivesse lógica, os testes ficariam acoplados à implementação. Ficou claro que ela deveria só repassar as chamadas, e a lógica tinha que ficar em outro lugar.

Os testes não tão preocupados que existem Services, Repositories ou Models lá. Qualquer mudança interna no sistema pode ser feita sem mexer na Facade, e os testes continuam funcionando normal.

**Aplicação no Projeto**

No MyFood, a classe Facade instancia e conecta os repositories e services principais. Ela possui os métodos chamados pelos scripts, como criarUsuario, criarEmpresa, criarPedido, criarEntrega e consultar atributos. A lógica desses comandos fica nos Services, então a Facade funciona como uma camada de entrada simples para os testes.

---

### Padrão Repository

**Descrição Geral**

O Repository é um padrão que isola o acesso a dados do resto da aplicação. Age como uma coleção de objetos, escondendo como os dados são salvos e recuperados. Quem usa um Repository não precisa saber se os dados tão em XML, banco de dados ou outro lugar qualquer.

**Problema Resolvido**

O padrão resolve o problema de classes que acumulam responsabilidade demais: aplicar regras do negócio E ao mesmo tempo saber como persistir dados. Quando essas duas coisas ficam juntas, uma mudança pequena na forma de salvar pode quebrar uma validação.

**Identificação da Oportunidade**

No início do projeto, as classes chamadas Manager faziam as duas coisas: validavam campos, aplicavam regras de criação, E além disso tinham salvarDados() e carregarDados() com XML. Ficou claro que salvarDados() não tem NADA a ver com "o nome do produto não pode duplicar". Eram dois problemas diferentes na mesma classe.

**Aplicação no Projeto**

Cada entidade principal tem seu Repository: UsuarioRepository, EmpresaRepository, ProdutoRepository, PedidoRepository e EntregaRepository. O Repository se preocupa com três coisas: salvar e carregar XML, gerar IDs sequenciais, e buscar por critérios simples.

O Service usa o Repository sem se preocupar se os dados tão em XML ou em outro lugar. Isso deixa as regras de negócio testáveis e a persistência fácil de trocar.

---

## User Stories implementadas

| US | Título | Testes |
|---|---|---|
| US1 | Criação de contas | us1_1, us1_2 |
| US2 | Criação de Restaurantes | us2_1, us2_2 |
| US3 | Criação de Produtos | us3_1, us3_2 |
| US4 | Criação de Pedidos | us4_1, us4_2 |
| US5 | Criação de Mercados | us5_1, us5_2 |
| US6 | Criação de Farmacia | us6_1, us6_2 |
| US7 | Criação de Entregador | us7_1, us7_2 |
| US8 | Sistema de entregas | us8_1, us8_2 |

---

## Milestones

| Milestone | User Stories | Foco |
|---|---|---|
| Milestone 1 | US1 a US4 | Contas, restaurantes, produtos e pedidos |
| Milestone 2 | US5 a US8 | Mercados, farmacias, entregadores e entregas |

---

## Persistência

Os dados são salvos em XML usando java.beans.XMLEncoder e carregados com java.beans.XMLDecoder. Cada entidade tem seu arquivo:

| Arquivo | Conteúdo |
|---|---|
| dados/usuarios.xml | usuários + próximo ID |
| dados/empresas.xml | empresas + próximo ID |
| dados/produtos.xml | produtos + próximo ID |
| dados/pedidos.xml | pedidos + próximo número |
| dados/entregas.xml | entregas + próximo ID |

Os arquivos são atualizados só quando encerrarSistema é chamado. O zerarSistema limpa tudo da memória mas não mexe nos arquivos, eles só mudam no próximo encerrarSistema.
