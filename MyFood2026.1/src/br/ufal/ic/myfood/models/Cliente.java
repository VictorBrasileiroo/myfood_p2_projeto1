package br.ufal.ic.myfood.models;

public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;

    public Cliente() {}

    public Cliente(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha, endereco);
    }

    @Override
    public String getTipo() {
        return "cliente";
    }
}
