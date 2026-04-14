package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    private static final String ARQUIVO_USUARIOS = "dados/usuarios.xml";

    private List<Usuario> usuarioList;
    private int proximoId;

    public UsuarioManager() {
        this.usuarioList = new ArrayList<>();
        this.proximoId = 1;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_USUARIOS))) {
            encoder.writeObject(this.proximoId);
            encoder.writeObject(this.usuarioList);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_USUARIOS);
        if (!arquivo.exists()) {
            return;
        }
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoId = (int) decoder.readObject();
            this.usuarioList = (List<Usuario>) decoder.readObject();
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarEmailDuplicado(email);

        usuarioList.add(new Cliente(proximoId++, nome, email, senha, endereco));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarCpf(cpf);
        validarEmailDuplicado(email);

        usuarioList.add(new DonoDeEmpresa(proximoId++, nome, email, senha, endereco, cpf));
    }
    
    public int login(String email, String senha) throws LoginSenhaInvalidosException {
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginSenhaInvalidosException();
        }
        for (Usuario usuario : usuarioList) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario.getId();
            }
        }
        throw new LoginSenhaInvalidosException();
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {
        int idInt;
        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new UsuarioNaoExisteException();
        }

        for (Usuario usuario : usuarioList) {
            if (usuario.getId() == idInt) {
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
                    default:
                        throw new AtributoInvalidoExc();
                }
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public Usuario buscarPorId(int id) throws UsuarioNaoExisteException {
        for (Usuario usuario : usuarioList) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public boolean ehDonoDeEmpresa(int id) {
        for (Usuario usuario : usuarioList) {
            if (usuario.getId() == id) {
                return usuario instanceof DonoDeEmpresa;
            }
        }
        return false;
    }

    private void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }
    }

    private void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new EmailInvalidoException();
        }
    }

    private void validarSenha(String senha) throws SenhaInvalidoException {
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidoException();
        }
    }

    private void validarEndereco(String endereco) throws EnderecoInvalidoException {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoInvalidoException();
        }
    }

    private void validarCpf(String cpf) throws CpfInvalidoException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new CpfInvalidoException();
        }
        String digitos = cpf.replaceAll("[^0-9]", "");
        if (digitos.length() != 11) {
            throw new CpfInvalidoException();
        }
    }

    private void validarEmailDuplicado(String email) throws UsuarioJaExisteException {
        for (Usuario usuario : usuarioList) {
            if (usuario.getEmail().equals(email)) {
                throw new UsuarioJaExisteException();
            }
        }
    }
}
