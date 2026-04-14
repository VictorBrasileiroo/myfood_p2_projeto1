package br.ufal.ic.myfood;

import br.ufal.ic.myfood.models.EmpresaManager;
import br.ufal.ic.myfood.models.UsuarioManager;

public class Facade {

    private UsuarioManager userManager;
    private EmpresaManager empresaManager;

    public Facade() {
        this.userManager = new UsuarioManager();
        this.empresaManager = new EmpresaManager(this.userManager);
        try {
            this.userManager.carregarDados();
        } catch (Exception e) {
        }
    }

    public void zerarSistema() {
        this.userManager = new UsuarioManager();
        this.empresaManager = new EmpresaManager(this.userManager);
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

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return empresaManager.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        return empresaManager.getEmpresasDoUsuario(idDono);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        return empresaManager.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        return empresaManager.getIdEmpresa(idDono, nome, indice);
    }
}
