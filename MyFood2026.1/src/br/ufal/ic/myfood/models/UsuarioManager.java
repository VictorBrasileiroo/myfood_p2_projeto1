package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;
import br.ufal.ic.myfood.exceptions.UsuarioJaExisteException;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioManager {

    List<Usuario> usuarioList;

    public UsuarioManager() {
        this.usuarioList = new ArrayList<Usuario>();
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws UsuarioJaExisteException {
        for (Usuario usuario : this.usuarioList) {
            if (usuario.getEmail().equals(email)) {
                throw new UsuarioJaExisteException();
            }
        }

        this.usuarioList.add(new Usuario(nome, email, senha, endereco, cpf));
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
