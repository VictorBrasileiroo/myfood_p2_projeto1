package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class EmpresaManager {

    private final List<Empresa> empresas;
    private int proximoId;
    private final UsuarioManager usuarioManager;

    public EmpresaManager(UsuarioManager usuarioManager) {
        this.empresas = new ArrayList<>();
        this.proximoId = 1;
        this.usuarioManager = usuarioManager;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) {
        int id = this.proximoId++;

        if (tipoEmpresa != null && tipoEmpresa.trim().equalsIgnoreCase("restaurante")) {
            this.empresas.add(new Restaurante(id, nome, endereco, dono, tipoCozinha));
        } else {
            this.empresas.add(new Restaurante(id, nome, endereco, dono, tipoCozinha));
        }

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

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws Exception {
        Empresa empresa = buscarPorId(idEmpresa);
        if (empresa == null) {
            return "";
        }

        if ("nome".equals(atributo)) {
            return empresa.getNome();
        }

        if ("endereco".equals(atributo)) {
            return empresa.getEndereco();
        }

        if ("tipoCozinha".equals(atributo) && empresa instanceof Restaurante) {
            return ((Restaurante) empresa).getTipoCozinha();
        }

        if ("dono".equals(atributo)) {
            Usuario dono = this.usuarioManager.buscarPorId(empresa.getDonoId());
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
}