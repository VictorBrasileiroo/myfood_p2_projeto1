package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Pedido;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {

    private static final String ARQUIVO_PEDIDOS = "dados/pedidos.xml";

    private List<Pedido> pedidos;
    private int proximoNumero;

    public PedidoRepository() {
        this.pedidos = new ArrayList<>();
        this.proximoNumero = 1;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) dir.mkdirs();
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_PEDIDOS))) {
            encoder.writeObject(this.proximoNumero);
            encoder.writeObject(this.pedidos);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_PEDIDOS);
        if (!arquivo.exists()) return;
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoNumero = (int) decoder.readObject();
            this.pedidos = (List<Pedido>) decoder.readObject();
        }
    }

    public void salvar(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public Pedido buscarPorNumero(int numero) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == numero) return p;
        }
        return null;
    }

    public List<Pedido> listarPorClienteEEmpresa(int clienteId, int empresaId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getClienteId() == clienteId && p.getEmpresaId() == empresaId) resultado.add(p);
        }
        return resultado;
    }

    public Pedido buscarAbertoporClienteEEmpresa(int clienteId, int empresaId) {
        for (Pedido p : pedidos) {
            if (p.getClienteId() == clienteId && p.getEmpresaId() == empresaId
                    && "aberto".equals(p.getEstado())) {
                return p;
            }
        }
        return null;
    }

    public List<Pedido> listarTodos() {
        return pedidos;
    }

    public int gerarNumero() {
        return this.proximoNumero++;
    }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
    public int getProximoNumero() { return proximoNumero; }
    public void setProximoNumero(int proximoNumero) { this.proximoNumero = proximoNumero; }
}
