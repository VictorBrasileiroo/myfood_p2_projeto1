package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.Entregador;
import br.ufal.ic.myfood.models.Usuario;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private static final String ARQUIVO_USUARIOS = "dados/usuarios.xml";

    private List<Usuario> usuarios;
    private int proximoId;

    public UsuarioRepository() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) dir.mkdirs();
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_USUARIOS))) {
            encoder.writeObject(this.proximoId);
            encoder.writeObject(this.usuarios);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_USUARIOS);
        if (!arquivo.exists()) return;
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoId = (int) decoder.readObject();
            this.usuarios = (List<Usuario>) decoder.readObject();
        }
    }

    public void salvar(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public Usuario buscarPorId(int id) throws UsuarioNaoExisteException {
        for (Usuario u : usuarios) {
            if (u.getId() == id) return u;
        }
        throw new UsuarioNaoExisteException();
    }

    public Usuario buscarPorEmailESenha(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) return u;
        }
        return null;
    }

    public boolean existeComEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) return true;
        }
        return false;
    }

    public boolean existeComPlaca(String placa) {
        for (Usuario u : usuarios) {
            if (u instanceof Entregador && ((Entregador) u).getPlaca().equals(placa)) return true;
        }
        return false;
    }

    public int gerarId() {
        return this.proximoId++;
    }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}
