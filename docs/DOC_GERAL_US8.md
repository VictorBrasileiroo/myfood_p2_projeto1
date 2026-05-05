# Correcao US8

### US8_1
Principais ajustes realizados:
- Inclusao do metodo `liberarPedido` para mudar pedido de `preparando` para `pronto`
- Validacao de pedido ja liberado e pedido que ainda nao esta em preparo
- Inclusao do metodo `obterPedido` para entregadores
- Priorizacao de pedidos de farmacia quando o entregador trabalha em farmacia
- Validacao de entregador sem empresa vinculada
- Criacao da entidade `Entrega` com pedido, entregador e destino
- Criacao do `EntregaRepository` para persistencia XML em `dados/entregas.xml`
- Criacao do `EntregaService` com as regras de entrega
- Inclusao dos metodos `criarEntrega`, `getEntrega`, `getIdEntrega` e `entregar` na facade
- `criarEntrega` muda o estado do pedido para `entregando`
- Destino vazio usa o endereco do cliente
- `entregar` muda o estado do pedido para `entregue`
- Validacao para impedir entregador em duas entregas abertas ao mesmo tempo
- `getEntrega` retorna cliente, empresa, pedido, entregador, destino e produtos
- Padronizacao das mensagens de excecao para casar com os scripts EasyAccept

Resultado validado:
- `us8_1.txt` com 99 testes OK

### US8_2
Principais ajustes realizados:
- Persistencia de entregas com XML (`dados/entregas.xml`) incluindo proximo id e lista de entregas
- Carregamento das entregas no construtor da facade
- Salvamento das entregas no metodo `encerrarSistema`
- Manutencao dos estados dos pedidos apos reinicio do sistema
- Manutencao correta dos dados de entrega apos reinicio

Resultado validado:
- `us8_2.txt` com 31 testes OK

## Arquivos principais criados/alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Entrega.java
- MyFood2026.1/src/br/ufal/ic/myfood/repositories/EntregaRepository.java
- MyFood2026.1/src/br/ufal/ic/myfood/repositories/PedidoRepository.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/EntregaService.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/PedidoService.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/EmpresaService.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoJaLiberadoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoNaoPreparandoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EntregadorSemEmpresaException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoParaEntregaNaoExisteException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EntregadorInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PedidoNaoProntoEntregaException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EntregadorEmEntregaException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EntregaNaoExisteException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EntregaInexistenteException.java
