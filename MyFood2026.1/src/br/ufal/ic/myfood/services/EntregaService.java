package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.*;
import br.ufal.ic.myfood.repositories.EntregaRepository;

import java.io.IOException;

public class EntregaService {

    private final EntregaRepository repository;
    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final ProdutoService produtoService;

    public EntregaService(EntregaRepository repository, PedidoService pedidoService, UsuarioService usuarioService,
                          EmpresaService empresaService, ProdutoService produtoService) {
        this.repository = repository;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.produtoService = produtoService;
    }

    public void salvarDados() throws IOException {
        repository.salvarDados();
    }

    public void carregarDados() throws IOException {
        repository.carregarDados();
    }

    public int criarEntrega(int pedidoId, int entregadorId, String destino) throws Exception {
        Pedido pedido = pedidoService.buscarPorNumero(pedidoId);
        if (!"pronto".equals(pedido.getEstado())) throw new PedidoNaoProntoEntregaException();
        if (!usuarioService.ehEntregador(entregadorId)) throw new EntregadorInvalidoException();
        if (entregadorEstaEmEntrega(entregadorId)) throw new EntregadorEmEntregaException();

        Usuario cliente = usuarioService.buscarPorId(pedido.getClienteId());
        String endereco = destino;
        if (endereco == null || endereco.trim().isEmpty()) endereco = cliente.getEndereco();

        int id = repository.gerarId();
        repository.salvar(new Entrega(id, pedidoId, entregadorId, endereco));
        pedidoService.marcarEntregando(pedidoId);
        return id;
    }

    public String getEntrega(int id, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoExc();

        Entrega entrega = repository.buscarPorId(id);
        Pedido pedido = pedidoService.buscarPorNumero(entrega.getPedidoId());

        switch (atributo) {
            case "cliente":
                return usuarioService.buscarPorId(pedido.getClienteId()).getNome();
            case "empresa":
                return empresaService.buscarPorId(pedido.getEmpresaId()).getNome();
            case "pedido":
                return String.valueOf(entrega.getPedidoId());
            case "entregador":
                return usuarioService.buscarPorId(entrega.getEntregadorId()).getNome();
            case "destino":
                return entrega.getDestino();
            case "produtos":
                return formatarProdutos(pedido);
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public int getIdEntrega(int pedidoId) throws EntregaNaoExisteException {
        Entrega entrega = repository.buscarPorPedido(pedidoId);
        if (entrega == null) throw new EntregaNaoExisteException();
        return entrega.getId();
    }

    private boolean entregadorEstaEmEntrega(int entregadorId) {
        for (Entrega entrega : repository.listarTodos()) {
            Pedido pedido = pedidoService.buscarPorNumero(entrega.getPedidoId());
            if (entrega.getEntregadorId() == entregadorId && "entregando".equals(pedido.getEstado())) {
                return true;
            }
        }
        return false;
    }

    private String formatarProdutos(Pedido pedido) {
        if (pedido.getProdutosIds().isEmpty()) return "{[]}";

        StringBuilder sb = new StringBuilder("{[");
        for (int i = 0; i < pedido.getProdutosIds().size(); i++) {
            Produto produto = produtoService.buscarPorId(pedido.getProdutosIds().get(i));
            sb.append(produto.getNome());
            if (i < pedido.getProdutosIds().size() - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}
