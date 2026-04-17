package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Empresa;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository {

    private static final String ARQUIVO_EMPRESAS = "dados/empresas.xml";

    private List<Empresa> empresas;
    private int proximoId;

    public EmpresaRepository() {
        this.empresas = new ArrayList<>();
        this.proximoId = 1;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) dir.mkdirs();
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_EMPRESAS))) {
            encoder.writeObject(this.proximoId);
            encoder.writeObject(this.empresas);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_EMPRESAS);
        if (!arquivo.exists()) return;
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoId = (int) decoder.readObject();
            this.empresas = (List<Empresa>) decoder.readObject();
        }
    }

    public void salvar(Empresa empresa) {
        this.empresas.add(empresa);
    }

    public Empresa buscarPorId(int id) {
        for (Empresa e : empresas) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public List<Empresa> listarTodos() {
        return empresas;
    }

    public int gerarId() {
        return this.proximoId++;
    }

    public List<Empresa> getEmpresas() { return empresas; }
    public void setEmpresas(List<Empresa> empresas) { this.empresas = empresas; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}
