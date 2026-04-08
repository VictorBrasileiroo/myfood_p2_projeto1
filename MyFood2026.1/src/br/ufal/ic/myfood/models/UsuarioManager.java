package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    List<Usuario> usuarioList;

    public UsuarioManager() {
        this.usuarioList = new ArrayList<Usuario>();
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
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

        if (cpf != null && !isValidCpf(cpf)) {
            throw new CpfInvalidoException();
        }

        for (Usuario usuario : this.usuarioList) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(email)) {
                throw new UsuarioJaExisteException();
            }
        }

        if (!email.contains("@")) {
            throw new EmailInvalidoException();
        }

        this.usuarioList.add(new Usuario(nome, email, senha, endereco, cpf));
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

    public String login(String email, String senha) {
        for (Usuario usuario : this.usuarioList) {
            if (usuario.getEmail().equals(email)) {
                if (usuario.getSenha().equals(senha)) {
                    return usuario.getId();
                }
            }
        }
        return null;
    }

}
