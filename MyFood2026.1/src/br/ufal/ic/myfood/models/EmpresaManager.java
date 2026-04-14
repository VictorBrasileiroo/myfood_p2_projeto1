package br.ufal.ic.myfood.models;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaManager {

    private static final String ARQUIVO_EMPRESAS = "dados/empresas.xml";

    private List<Empresa> empresas = new ArrayList<>();
    private int proximoId = 1;
    private UsuarioManager usuarioManager;

    public EmpresaManager(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_EMPRESAS))) {
            encoder.writeObject(this.proximoId);
            encoder.writeObject(this.empresas);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_EMPRESAS);
        if (!arquivo.exists()) {
            return;
        }

        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoId = (int) decoder.readObject();
            this.empresas = (List<Empresa>) decoder.readObject();
        }
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        int id = this.proximoId++;
        this.empresas.add(new Restaurante(id, nome, endereco, dono, tipoCozinha));

        return id;
    }

    public String getEmpresasDoUsuario(int idDono) {
        StringBuilder resultado = new StringBuilder("{");
        boolean primeira = true;

        for (Empresa empresa : this.empresas) {
            if (empresa.getDonoId() == idDono) {
                if (primeira) {
                    resultado.append("[");
                    primeira = false;
                } else {
                    resultado.append(", ");
                }
                resultado.append("[")
                        .append(empresa.getNome())
                        .append(", ")
                        .append(empresa.getEndereco())
                        .append("]");
            }
        }

        if (primeira) {
            resultado.append("[]");
        } else {
            resultado.append("]");
        }

        resultado.append("}");
        return resultado.toString();
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        Empresa empresaEncontrada = buscarPorId(empresa);
        if (empresaEncontrada == null) {
            return "";
        }

        if ("nome".equals(atributo)) {
            return empresaEncontrada.getNome();
        }

        if ("endereco".equals(atributo)) {
            return empresaEncontrada.getEndereco();
        }

        if ("tipoCozinha".equals(atributo) && empresaEncontrada instanceof Restaurante) {
            return ((Restaurante) empresaEncontrada).getTipoCozinha();
        }

        if ("dono".equals(atributo)) {
            Usuario dono = this.usuarioManager.buscarPorId(empresaEncontrada.getDonoId());
            return dono.getNome();
        }

        return "";
    }

    public int getIdEmpresa(int idDono, String nome, int indice) {
        int contador = 0;
        for (Empresa empresa : this.empresas) {
            if (empresa.getDonoId() == idDono && nome != null && nome.equals(empresa.getNome())) {
                if (contador == indice) {
                    return empresa.getId();
                }
                contador++;
            }
        }
        return -1;
    }

    public Empresa buscarPorId(int id) {
        for (Empresa empresa : this.empresas) {
            if (empresa.getId() == id) {
                return empresa;
            }
        }
        return null;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public int getProximoId() {
        return proximoId;
    }

    public void setUsuarioManager(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
    }

    public void zerarDados() {
        this.empresas = new ArrayList<>();
        this.proximoId = 1;
    }
}