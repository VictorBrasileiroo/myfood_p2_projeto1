package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Entrega;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaRepository {

    private static final String ARQUIVO_ENTREGAS = "dados/entregas.xml";

    private List<Entrega> entregas;
    private int proximoId;

    public EntregaRepository() {
        this.entregas = new ArrayList<>();
        this.proximoId = 1;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) dir.mkdirs();
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_ENTREGAS))) {
            encoder.writeObject(this.proximoId);
            encoder.writeObject(this.entregas);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_ENTREGAS);
        if (!arquivo.exists()) return;
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoId = (int) decoder.readObject();
            this.entregas = (List<Entrega>) decoder.readObject();
        }
    }

    public void salvar(Entrega entrega) {
        this.entregas.add(entrega);
    }

    public Entrega buscarPorId(int id) {
        for (Entrega entrega : entregas) {
            if (entrega.getId() == id) return entrega;
        }
        return null;
    }

    public Entrega buscarPorPedido(int pedidoId) {
        for (Entrega entrega : entregas) {
            if (entrega.getPedidoId() == pedidoId) return entrega;
        }
        return null;
    }

    public List<Entrega> listarTodos() {
        return entregas;
    }

    public int gerarId() {
        return this.proximoId++;
    }

    public List<Entrega> getEntregas() { return entregas; }
    public void setEntregas(List<Entrega> entregas) { this.entregas = entregas; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}
