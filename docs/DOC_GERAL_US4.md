# Correcao US4

### US4_1
Principais ajustes realizados:
- Inclusao dos metodos `criarPedido`, `adicionarProduto`, `getPedidos`, `fecharPedido`, `removerProduto` e `getNumeroPedido` na facade
- Criacao da entidade `Pedido` com campos numero, clienteId, empresaId, estado e lista de produtosIds
- Estado inicial do pedido definido como `"aberto"`, alterado para `"preparando"` ao fechar
- Criacao do `PedidoRepository` para persistencia XML em `dados/pedidos.xml`
- Criacao do `PedidoService` com as regras de negocio da US4
- Validacao que somente clientes (nao donos de empresa) podem criar pedidos
- Validacao que nao e permitido ter dois pedidos abertos para a mesma empresa pelo mesmo cliente
- `adicionarProduto` valida que o produto pertence a empresa do pedido antes de adicionar
- `adicionarProduto` e `removerProduto` lancan excecao se o pedido ja estiver fechado
- `removerProduto` remove apenas uma ocorrencia por nome, mantendo duplicatas restantes
- `getPedidos` com atributo `produtos` retorna os nomes na ordem de insercao
- `getPedidos` com atributo `valor` retorna a soma formatada com `Locale.US` (duas casas decimais)
- `getNumeroPedido` retorna o numero do pedido por indice na ordem de criacao (mais antigo primeiro)
- Ordem de validacao de `removerProduto`: nome invalido -> pedido fechado -> produto nao encontrado
- Padronizacao das mensagens de excecao para casar com o script EasyAccept (incluindo typo "adcionar" no teste)

Resultado validado:
- `us4_1.txt` com 40 testes OK

### US4_2
Principais ajustes realizados:
- Persistencia de pedidos com XML (`dados/pedidos.xml`) incluindo `proximoNumero` e lista de pedidos
- Carregamento dos dados de pedidos no construtor da facade
- Salvamento dos dados de pedidos no metodo `encerrarSistema`
- Manutencao correta do estado dos pedidos e lista de produtos apos reinicio do sistema

Resultado validado:
- `us4_2.txt` com 13 testes OK

## Arquivos principais criados/alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Pedido.java
- MyFood2026.1/src/br/ufal/ic/myfood/repositories/PedidoRepository.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/PedidoService.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/DonoNaoPodePedirException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoDuplicadoAbertoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoNaoExisteAbertoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/ProdutoNaoPertenceEmpresaException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoNaoEncontradoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoFechadoAdicionarException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoFechadoRemoverException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/ProdutoInvalidoException.java
