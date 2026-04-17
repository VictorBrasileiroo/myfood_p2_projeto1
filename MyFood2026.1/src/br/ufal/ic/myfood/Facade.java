package br.ufal.ic.myfood;

import br.ufal.ic.myfood.repositories.EmpresaRepository;
import br.ufal.ic.myfood.repositories.ProdutoRepository;
import br.ufal.ic.myfood.repositories.UsuarioRepository;
import br.ufal.ic.myfood.services.EmpresaService;
import br.ufal.ic.myfood.services.ProdutoService;
import br.ufal.ic.myfood.services.UsuarioService;

public class Facade {

    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;

    public Facade() {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepo);

        EmpresaRepository empresaRepo = new EmpresaRepository();
        this.empresaService = new EmpresaService(empresaRepo, this.usuarioService);

        ProdutoRepository produtoRepo = new ProdutoRepository();
        this.produtoService = new ProdutoService(produtoRepo, this.empresaService);

        try { usuarioRepo.carregarDados(); } catch (Exception e) {}
        try { empresaRepo.carregarDados(); } catch (Exception e) {}
        try { produtoRepo.carregarDados(); } catch (Exception e) {}
    }

    public void zerarSistema() {
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepo);

        EmpresaRepository empresaRepo = new EmpresaRepository();
        this.empresaService = new EmpresaService(empresaRepo, this.usuarioService);

        ProdutoRepository produtoRepo = new ProdutoRepository();
        this.produtoService = new ProdutoService(produtoRepo, this.empresaService);
    }

    public void encerrarSistema() {
        try { usuarioService.salvarDados(); } catch (Exception e) {}
        try { empresaService.salvarDados(); } catch (Exception e) {}
        try { produtoService.salvarDados(); } catch (Exception e) {}
    }

    // US1
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

    // US2
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
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

    // US3
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
}
