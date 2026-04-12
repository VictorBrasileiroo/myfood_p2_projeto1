package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    private static final String ARQUIVO_USUARIOS = "dados_usuarios.dat";

    List<Usuario> usuarioList;

    public UsuarioManager() {
        this.usuarioList = new ArrayList<Usuario>();
    }

    public void salvarDados() throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(ARQUIVO_USUARIOS))) {
            output.writeObject(this.usuarioList);
        }
    }

    public void carregarDados() throws IOException, ClassNotFoundException {
        File arquivo = new File(ARQUIVO_USUARIOS);
        if (!arquivo.exists()) {
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object dadosLidos = input.readObject();

            if (!(dadosLidos instanceof List<?>)) {
                throw new IOException("Formato de dados invalido.");
            }

            List<?> listaLida = (List<?>) dadosLidos;
            List<Usuario> usuariosCarregados = new ArrayList<Usuario>();

            for (Object item : listaLida) {
                if (!(item instanceof Usuario)) {
                    throw new IOException("Formato de dados invalido.");
                }
                usuariosCarregados.add((Usuario) item);
            }

            this.usuarioList = usuariosCarregados;
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        validarCamposBasicos(nome, email, senha, endereco);
        validarEmailDuplicado(email);

        if (!email.contains("@")) {
            throw new EmailInvalidoException();
        }

        this.usuarioList.add(new Usuario(nome, email, senha, endereco, null));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarCamposBasicos(nome, email, senha, endereco);

        if (!isValidCpf(cpf)) {
            throw new CpfInvalidoException();
        }

        validarEmailDuplicado(email);

        if (!email.contains("@")) {
            throw new EmailInvalidoException();
        }

        this.usuarioList.add(new Usuario(nome, email, senha, endereco, cpf));
    }

    private void validarCamposBasicos(String nome, String email, String senha, String endereco) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (email == null || email.trim().isEmpty()) {
            throw new EmailInvalidoException();
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidoException();
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EnderecoInvalidoException();
        }
    }

    private void validarEmailDuplicado(String email) throws UsuarioJaExisteException {
        for (Usuario usuario : this.usuarioList) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(email)) {
                throw new UsuarioJaExisteException();
            }
        }
    }

    private boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }
        String digitos = cpf.replaceAll("[^0-9]", "");
        return digitos.length() == 11;
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {
        for (Usuario usuario : this.usuarioList) {
            if (usuario.getId().equals(id)) {
                switch (atributo.toLowerCase()) {
                    case "nome":
                        return usuario.getNome();
                    case "email":
                        return usuario.getEmail();
                    case "endereco":
                        return usuario.getEndereco();
                    case "senha":
                        return usuario.getSenha();
                    case "cpf":
                        return usuario.getCpf();
                    default:
                        throw new AtributoInvalidoExc();
                }
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public String login(String email, String senha) throws LoginSenhaInvalidosException {
        if(email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginSenhaInvalidosException();
        }

        for (Usuario usuario : this.usuarioList) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(email)) {
                if (usuario.getSenha() != null && usuario.getSenha().equals(senha)) {
                    return usuario.getId();
                }
            }
        }
        throw new LoginSenhaInvalidosException();
    }

}
