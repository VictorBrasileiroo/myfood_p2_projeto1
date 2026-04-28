package br.ufal.ic.myfood;

import br.ufal.ic.myfood.repositories.EmpresaRepository;
import br.ufal.ic.myfood.repositories.PedidoRepository;
import br.ufal.ic.myfood.repositories.ProdutoRepository;
import br.ufal.ic.myfood.repositories.UsuarioRepository;
import br.ufal.ic.myfood.services.EmpresaService;
import br.ufal.ic.myfood.services.PedidoService;
import br.ufal.ic.myfood.services.ProdutoService;
import br.ufal.ic.myfood.services.UsuarioService;

public class Facade {

    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    public Facade() {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepo);

        EmpresaRepository empresaRepo = new EmpresaRepository();
        this.empresaService = new EmpresaService(empresaRepo, this.usuarioService);

        ProdutoRepository produtoRepo = new ProdutoRepository();
        this.produtoService = new ProdutoService(produtoRepo, this.empresaService);

        PedidoRepository pedidoRepo = new PedidoRepository();
        this.pedidoService = new PedidoService(pedidoRepo, this.usuarioService, this.empresaService, this.produtoService);

        try { usuarioRepo.carregarDados(); } catch (Exception e) {}
        try { empresaRepo.carregarDados(); } catch (Exception e) {}
        try { produtoRepo.carregarDados(); } catch (Exception e) {}
        try { pedidoRepo.carregarDados(); } catch (Exception e) {}
    }

    public void zerarSistema() {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepo);

        EmpresaRepository empresaRepo = new EmpresaRepository();
        this.empresaService = new EmpresaService(empresaRepo, this.usuarioService);

        ProdutoRepository produtoRepo = new ProdutoRepository();
        this.produtoService = new ProdutoService(produtoRepo, this.empresaService);

        PedidoRepository pedidoRepo = new PedidoRepository();
        this.pedidoService = new PedidoService(pedidoRepo, this.usuarioService, this.empresaService, this.produtoService);
    }

    public void encerrarSistema() {
        try { usuarioService.salvarDados(); } catch (Exception e) {}
        try { empresaService.salvarDados(); } catch (Exception e) {}
        try { produtoService.salvarDados(); } catch (Exception e) {}
        try { pedidoService.salvarDados(); } catch (Exception e) {}
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        usuarioService.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        usuarioService.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws Exception {
        return usuarioService.login(email, senha);
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {
        return usuarioService.getAtributoUsuario(id, atributo);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        return empresaService.getEmpresasDoUsuario(idDono);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        return empresaService.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        return empresaService.getIdEmpresa(idDono, nome, indice);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws Exception {
        empresaService.alterarFuncionamento(mercado, abre, fecha);
    }

    public int criarProduto(int empresa, String nome, double valor, String categoria) throws Exception {
        return produtoService.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, double valor, String categoria) throws Exception {
        produtoService.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        return produtoService.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws Exception {
        return produtoService.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        return pedidoService.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) throws Exception {
        pedidoService.adicionarProduto(numero, produto);
    }

    public String getPedidos(int numero, String atributo) throws Exception {
        return pedidoService.getPedidos(numero, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        pedidoService.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        pedidoService.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return pedidoService.getNumeroPedido(cliente, empresa, indice);
    }
}
