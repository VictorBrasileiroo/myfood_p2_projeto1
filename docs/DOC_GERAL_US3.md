# Correcao US3

### US3_1
Principais ajustes realizados:
- Inclusao dos metodos `criarProduto`, `editarProduto`, `getProduto` e `listarProdutos` na facade
- Criacao da entidade `Produto` com campos id, empresaId, nome, valor (double) e categoria
- Criacao do `ProdutoRepository` para persistencia XML em `dados/produtos.xml`
- Criacao do `ProdutoService` com as regras de negocio da US3
- Implementacao da ordem de validacao exigida: nome -> valor -> categoria -> duplicidade
- Formato de retorno de valor com duas casas decimais usando `Locale.US` para garantir ponto como separador decimal
- Formato de retorno de `listarProdutos` como `{[nome1, nome2]}` ou `{[]}` quando vazio
- `getProduto` com atributo `empresa` retorna o nome da empresa, nao o id
- Padronizacao das mensagens de excecao para casar com o script EasyAccept

Resultado validado:
- `us3_1.txt` com 25 testes OK

### US3_2
Principais ajustes realizados:
- Persistencia de produtos com XML (`dados/produtos.xml`) incluindo `proximoId` e lista de produtos
- Carregamento dos dados de produtos no construtor da facade
- Salvamento dos dados de produtos no metodo `encerrarSistema`
- Manutencao da compatibilidade de `getProduto` e `listarProdutos` apos reinicio do sistema

Resultado validado:
- `us3_2.txt` com 6 testes OK

## Arquivos principais criados/alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Produto.java
- MyFood2026.1/src/br/ufal/ic/myfood/repositories/ProdutoRepository.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/ProdutoService.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/ProdutoComNomeJaExisteException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/ValorInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/CategoriaInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/ProdutoNaoCadastradoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/ProdutoNaoEncontradoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/AtributoNaoExisteException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EmpresaNaoEncontradaException.java
