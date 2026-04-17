package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.repositories.ProdutoRepository;

import java.io.IOException;
import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;
    private final EmpresaService empresaService;

    public ProdutoService(ProdutoRepository repository, EmpresaService empresaService) {
        this.repository = repository;
        this.empresaService = empresaService;
    }

    public void salvarDados() throws IOException {
        repository.salvarDados();
    }

    public void carregarDados() throws IOException {
        repository.carregarDados();
    }

    public int criarProduto(int empresaId, String nome, double valor, String categoria) throws Exception {
        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        if (repository.buscarPorNomeEEmpresa(nome, empresaId) != null) {
            throw new ProdutoComNomeJaExisteException();
        }

        int id = repository.gerarId();
        repository.salvar(new Produto(id, empresaId, nome, valor, categoria));
        return id;
    }

    public void editarProduto(int produtoId, String nome, double valor, String categoria) throws Exception {
        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        Produto produto = repository.buscarPorId(produtoId);
        if (produto == null) throw new ProdutoNaoCadastradoException();

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String getProduto(String nome, int empresaId, String atributo) throws Exception {
        Produto produto = repository.buscarPorNomeEEmpresa(nome, empresaId);
        if (produto == null) throw new ProdutoNaoEncontradoException();

        switch (atributo) {
            case "valor":    return String.format(java.util.Locale.US, "%.2f", produto.getValor());
            case "categoria": return produto.getCategoria();
            case "empresa":
                Empresa empresa = empresaService.buscarPorId(empresaId);
                return empresa.getNome();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String listarProdutos(int empresaId) throws Exception {
        if (empresaService.buscarPorId(empresaId) == null) throw new EmpresaNaoEncontradaException();

        List<Produto> lista = repository.listarPorEmpresa(empresaId);

        if (lista.isEmpty()) return "{[]}";

        StringBuilder sb = new StringBuilder("{[");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i).getNome());
            if (i < lista.size() - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }

    public Produto buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    private void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
    }

    private void validarValor(double valor) throws ValorInvalidoException {
        if (valor < 0) throw new ValorInvalidoException();
    }

    private void validarCategoria(String categoria) throws CategoriaInvalidoException {
        if (categoria == null || categoria.trim().isEmpty()) throw new CategoriaInvalidoException();
    }
}
