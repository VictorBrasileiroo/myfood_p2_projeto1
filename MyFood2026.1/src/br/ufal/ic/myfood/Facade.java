package br.ufal.ic.myfood;

import br.ufal.ic.myfood.models.UsuarioManager;

public class Facade {

    private UsuarioManager userManager;

    public Facade() {
        this.userManager = new UsuarioManager();
        try {
            this.userManager.carregarDados();
        } catch (Exception e) {
        }
    }

    public void zerarSistema() {
        this.userManager = new UsuarioManager();
    }

    public void encerrarSistema() {
        try {
            this.userManager.salvarDados();
        } catch (Exception e) {
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        userManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        userManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws Exception {
        return userManager.login(email, senha);
    }

    public String getAtributoUsuario(String id, String atributo) throws Exception {
        return userManager.getAtributoUsuario(id, atributo);
    }
}
