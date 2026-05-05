package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.repositories.PedidoRepository;

import java.io.IOException;
import java.util.List;

public class PedidoService {

    private final PedidoRepository repository;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository repository, UsuarioService usuarioService,
                         EmpresaService empresaService, ProdutoService produtoService) {
        this.repository = repository;
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

    public int criarPedido(int clienteId, int empresaId) throws Exception {
        if (usuarioService.ehDonoDeEmpresa(clienteId)) throw new DonoNaoPodePedirException();

        if (repository.buscarAbertoporClienteEEmpresa(clienteId, empresaId) != null) {
            throw new PedidoDuplicadoAbertoException();
        }

        int numero = repository.gerarNumero();
        repository.salvar(new Pedido(numero, clienteId, empresaId));
        return numero;
    }

    public void adicionarProduto(int numero, int produtoId) throws Exception {
        Pedido pedido = repository.buscarPorNumero(numero);
        if (pedido == null) throw new PedidoNaoExisteAbertoException();

        Produto produto = produtoService.buscarPorId(produtoId);
        if (produto == null || produto.getEmpresaId() != pedido.getEmpresaId()) {
            throw new ProdutoNaoPertenceEmpresaException();
        }

        if ("preparando".equals(pedido.getEstado())) throw new PedidoFechadoAdicionarException();

        pedido.getProdutosIds().add(produtoId);
    }

    public String getPedidos(int numero, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoExc();

        Pedido pedido = repository.buscarPorNumero(numero);

        switch (atributo) {
            case "cliente":
                Usuario cliente = usuarioService.buscarPorId(pedido.getClienteId());
                return cliente.getNome();
            case "empresa":
                return empresaService.buscarPorId(pedido.getEmpresaId()).getNome();
            case "estado":
                return pedido.getEstado();
            case "produtos":
                return formatarProdutos(pedido);
            case "valor":
                return calcularValor(pedido);
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public void fecharPedido(int numero) throws Exception {
        Pedido pedido = repository.buscarPorNumero(numero);
        if (pedido == null) throw new PedidoNaoEncontradoException();
        pedido.setEstado("preparando");
    }

    public Pedido buscarPorNumero(int numero) {
        return repository.buscarPorNumero(numero);
    }

    public void marcarEntregando(int numero) {
        repository.buscarPorNumero(numero).setEstado("entregando");
    }

    public void marcarEntregue(int numero) {
        repository.buscarPorNumero(numero).setEstado("entregue");
    }

    public void liberarPedido(int numero) throws Exception {
        Pedido pedido = repository.buscarPorNumero(numero);

        if ("pronto".equals(pedido.getEstado())) throw new PedidoJaLiberadoException();
        if (!"preparando".equals(pedido.getEstado())) throw new PedidoNaoPreparandoException();

        pedido.setEstado("pronto");
    }

    public int obterPedido(int entregadorId) throws Exception {
        if (!usuarioService.ehEntregador(entregadorId)) throw new UsuarioNaoEntregadorException();
        if (!empresaService.entregadorTemEmpresa(entregadorId)) throw new EntregadorSemEmpresaException();

        Pedido primeiroPedido = null;
        Pedido primeiroPedidoFarmacia = null;

        for (Pedido pedido : repository.listarTodos()) {
            if (!"pronto".equals(pedido.getEstado())) continue;
            if (!empresaService.entregadorTrabalhaNaEmpresa(pedido.getEmpresaId(), entregadorId)) continue;

            if (empresaService.ehFarmacia(pedido.getEmpresaId())) {
                if (primeiroPedidoFarmacia == null) primeiroPedidoFarmacia = pedido;
            } else if (primeiroPedido == null) {
                primeiroPedido = pedido;
            }
        }

        if (primeiroPedidoFarmacia != null) return primeiroPedidoFarmacia.getNumero();
        if (primeiroPedido != null) return primeiroPedido.getNumero();

        throw new PedidoParaEntregaNaoExisteException();
    }

    public void removerProduto(int numero, String nomeProduto) throws Exception {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) throw new ProdutoInvalidoException();

        Pedido pedido = repository.buscarPorNumero(numero);

        if ("preparando".equals(pedido.getEstado())) throw new PedidoFechadoRemoverException();

        List<Integer> ids = pedido.getProdutosIds();
        for (int i = 0; i < ids.size(); i++) {
            Produto p = produtoService.buscarPorId(ids.get(i));
            if (p != null && p.getNome().equals(nomeProduto)) {
                ids.remove(i);
                return;
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    public int getNumeroPedido(int clienteId, int empresaId, int indice) throws Exception {
        List<Pedido> lista = repository.listarPorClienteEEmpresa(clienteId, empresaId);
        return lista.get(indice).getNumero();
    }

    private String formatarProdutos(Pedido pedido) {
        List<Integer> ids = pedido.getProdutosIds();
        if (ids.isEmpty()) return "{[]}";

        StringBuilder sb = new StringBuilder("{[");
        for (int i = 0; i < ids.size(); i++) {
            Produto p = produtoService.buscarPorId(ids.get(i));
            sb.append(p.getNome());
            if (i < ids.size() - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }

    private String calcularValor(Pedido pedido) {
        double total = 0;
        for (int id : pedido.getProdutosIds()) {
            Produto p = produtoService.buscarPorId(id);
            if (p != null) total += p.getValor();
        }
        return String.format(java.util.Locale.US, "%.2f", total);
    }
}
