package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;
import br.ufal.ic.myfood.exceptions.EmpresaComNomeJaExisteException;
import br.ufal.ic.myfood.exceptions.EmpresaNaoCadastradaException;
import br.ufal.ic.myfood.exceptions.EmpresaNomeELocalRepetidosException;
import br.ufal.ic.myfood.exceptions.IndiceInvalidoException;
import br.ufal.ic.myfood.exceptions.IndiceMaiorQueEsperadoException;
import br.ufal.ic.myfood.exceptions.NaoExisteEmpresaComEsseNomeException;
import br.ufal.ic.myfood.exceptions.NomeInvalidoException;
import br.ufal.ic.myfood.exceptions.UsuarioNaoPodeCriarEmpresaException;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        validarUsuarioDono(dono);

        for (Empresa empresa : this.empresas) {
            if (empresa.getDonoId() == dono
                    && Objects.equals(empresa.getNome(), nome)
                    && Objects.equals(empresa.getEndereco(), endereco)) {
                throw new EmpresaNomeELocalRepetidosException();
            }
        }

        for (Empresa empresa : this.empresas) {
            if (empresa.getDonoId() != dono && Objects.equals(empresa.getNome(), nome)) {
                throw new EmpresaComNomeJaExisteException();
            }
        }

        int id = this.proximoId++;
        this.empresas.add(new Restaurante(id, nome, endereco, dono, tipoCozinha));

        return id;
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        validarUsuarioDono(idDono);

        List<Empresa> empresasDoDono = new ArrayList<>();
        for (Empresa empresa : this.empresas) {
            if (empresa.getDonoId() == idDono) {
                empresasDoDono.add(empresa);
            }
        }

        if (empresasDoDono.isEmpty()) {
            return "{[]}";
        }

        StringBuilder resultado = new StringBuilder("{[");
        for (int i = 0; i < empresasDoDono.size(); i++) {
            Empresa empresa = empresasDoDono.get(i);
            resultado.append("[")
                    .append(empresa.getNome())
                    .append(", ")
                    .append(empresa.getEndereco())
                    .append("]");

            if (i < empresasDoDono.size() - 1) {
                resultado.append(", ");
            }
        }
        resultado.append("]}");
        return resultado.toString();
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        Empresa empresaEncontrada = buscarPorId(empresa);
        if (empresaEncontrada == null) {
            throw new EmpresaNaoCadastradaException();
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoExc();
        }

        String atributoNormalizado = atributo.trim();

        if ("nome".equals(atributoNormalizado)) {
            return empresaEncontrada.getNome();
        }

        if ("endereco".equals(atributoNormalizado)) {
            return empresaEncontrada.getEndereco();
        }

        if ("tipoCozinha".equals(atributoNormalizado)) {
            if (empresaEncontrada instanceof Restaurante) {
                return ((Restaurante) empresaEncontrada).getTipoCozinha();
            }
            throw new AtributoInvalidoExc();
        }

        if ("dono".equals(atributoNormalizado)) {
            Usuario dono = this.usuarioManager.buscarPorId(empresaEncontrada.getDonoId());
            return dono.getNome();
        }

        throw new AtributoInvalidoExc();
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (indice < 0) {
            throw new IndiceInvalidoException();
        }

        validarUsuarioDono(idDono);

        List<Empresa> empresasComMesmoNome = new ArrayList<>();
        for (Empresa empresa : this.empresas) {
            if (empresa.getDonoId() == idDono && nome.equals(empresa.getNome())) {
                empresasComMesmoNome.add(empresa);
            }
        }

        if (empresasComMesmoNome.isEmpty()) {
            throw new NaoExisteEmpresaComEsseNomeException();
        }

        if (indice >= empresasComMesmoNome.size()) {
            throw new IndiceMaiorQueEsperadoException();
        }

        return empresasComMesmoNome.get(indice).getId();
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

    private void validarUsuarioDono(int idUsuario) throws UsuarioNaoPodeCriarEmpresaException {
        if (!this.usuarioManager.ehDonoDeEmpresa(idUsuario)) {
            throw new UsuarioNaoPodeCriarEmpresaException();
        }
    }
}