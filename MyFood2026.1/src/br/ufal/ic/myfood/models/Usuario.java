package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String senha, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getAtributo(String atributo) throws AtributoInvalidoExc {
        switch (atributo) {
            case "nome": return getNome();
            case "email": return getEmail();
            case "endereco": return getEndereco();
            case "senha": return getSenha();
            default: return getAtributoProprio(atributo);
        }
    }

    protected String getAtributoProprio(String atributo) throws AtributoInvalidoExc {
        throw new AtributoInvalidoExc();
    }

    public boolean ehDonoDeEmpresa() {
        return false;
    }

    public boolean ehEntregador() {
        return false;
    }

    public boolean temPlaca(String placa) {
        return false;
    }

    public abstract String getTipo();
}
