package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.UsuarioJaExisteException;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.UsuarioManager;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    UsuarioManager userManager;

    public Facade() {
        this.userManager = new UsuarioManager();
        try {
            this.userManager.carregarDados();
        } catch (Exception e) {
            // vazio para manter exceptions na facade
        }
    }


    public void zerarSistema() {
        this.userManager = new UsuarioManager();
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {
        return this.userManager.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws Exception {
        this.userManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws Exception {
        this.userManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public String login(String email, String senha) throws Exception {
        return this.userManager.login(email, senha);
    }

    public void encerrarSistema() {
        try {
            this.userManager.salvarDados();
        } catch (Exception e) {
            // vazio para manter exceptions na facade
        }
    }

}
