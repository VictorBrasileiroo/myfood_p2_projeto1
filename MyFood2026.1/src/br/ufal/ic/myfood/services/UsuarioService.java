package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Cliente;
import br.ufal.ic.myfood.models.DonoDeEmpresa;
import br.ufal.ic.myfood.models.Entregador;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.repositories.UsuarioRepository;

import java.io.IOException;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void salvarDados() throws IOException {
        repository.salvarDados();
    }

    public void carregarDados() throws IOException {
        repository.carregarDados();
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarEmailDuplicado(email);

        repository.salvar(new Cliente(repository.gerarId(), nome, email, senha, endereco));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarCpf(cpf);
        validarEmailDuplicado(email);

        repository.salvar(new DonoDeEmpresa(repository.gerarId(), nome, email, senha, endereco, cpf));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarVeiculo(veiculo);
        validarPlaca(placa);
        validarPlacaDuplicada(placa);
        validarEmailDuplicado(email);

        repository.salvar(new Entregador(repository.gerarId(), nome, email, senha, endereco, veiculo, placa));
    }

    public int login(String email, String senha) throws LoginSenhaInvalidosException {
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginSenhaInvalidosException();
        }
        Usuario usuario = repository.buscarPorEmailESenha(email, senha);
        if (usuario == null) throw new LoginSenhaInvalidosException();
        return usuario.getId();
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {
        int idInt;
        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new UsuarioNaoExisteException();
        }

        Usuario usuario = repository.buscarPorId(idInt);

        switch (atributo) {
            case "nome":     return usuario.getNome();
            case "email":    return usuario.getEmail();
            case "endereco": return usuario.getEndereco();
            case "senha":    return usuario.getSenha();
            case "cpf":
                if (usuario instanceof DonoDeEmpresa) {
                    return ((DonoDeEmpresa) usuario).getCpf();
                }
                throw new AtributoInvalidoExc();
            case "veiculo":
                if (usuario instanceof Entregador) {
                    return ((Entregador) usuario).getVeiculo();
                }
                throw new AtributoInvalidoExc();
            case "placa":
                if (usuario instanceof Entregador) {
                    return ((Entregador) usuario).getPlaca();
                }
                throw new AtributoInvalidoExc();
            default:
                throw new AtributoInvalidoExc();
        }
    }

    public Usuario buscarPorId(int id) throws UsuarioNaoExisteException {
        return repository.buscarPorId(id);
    }

    public boolean ehDonoDeEmpresa(int id) {
        try {
            return repository.buscarPorId(id) instanceof DonoDeEmpresa;
        } catch (UsuarioNaoExisteException e) {
            return false;
        }
    }

    public boolean ehEntregador(int id) {
        try {
            return repository.buscarPorId(id) instanceof Entregador;
        } catch (UsuarioNaoExisteException e) {
            return false;
        }
    }

    private void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
    }

    private void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) throw new EmailInvalidoException();
    }

    private void validarSenha(String senha) throws SenhaInvalidoException {
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidoException();
    }

    private void validarEndereco(String endereco) throws EnderecoInvalidoException {
        if (endereco == null || endereco.trim().isEmpty()) throw new EnderecoInvalidoException();
    }

    private void validarCpf(String cpf) throws CpfInvalidoException {
        if (cpf == null || cpf.trim().isEmpty()) throw new CpfInvalidoException();
        String digitos = cpf.replaceAll("[^0-9]", "");
        if (digitos.length() != 11) throw new CpfInvalidoException();
    }

    private void validarVeiculo(String veiculo) throws VeiculoInvalidoException {
        if (veiculo == null || veiculo.trim().isEmpty()) throw new VeiculoInvalidoException();
    }

    private void validarPlaca(String placa) throws PlacaInvalidoException {
        if (placa == null || placa.trim().isEmpty()) throw new PlacaInvalidoException();
    }

    private void validarEmailDuplicado(String email) throws UsuarioJaExisteException {
        if (repository.existeComEmail(email)) throw new UsuarioJaExisteException();
    }

    private void validarPlacaDuplicada(String placa) throws PlacaInvalidoException {
        if (repository.existeComPlaca(placa)) throw new PlacaInvalidoException();
    }
}
