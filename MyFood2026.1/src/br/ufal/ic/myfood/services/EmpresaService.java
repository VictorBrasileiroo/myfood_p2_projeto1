package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Farmacia;
import br.ufal.ic.myfood.models.Mercado;
import br.ufal.ic.myfood.models.Restaurante;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.repositories.EmpresaRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpresaService {

    private final EmpresaRepository repository;
    private final UsuarioService usuarioService;

    public EmpresaService(EmpresaRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public void salvarDados() throws IOException {
        repository.salvarDados();
    }

    public void carregarDados() throws IOException {
        repository.carregarDados();
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        validarDonoECadastroDisponivel(dono, nome, endereco);

        int id = repository.gerarId();
        repository.salvar(new Restaurante(id, nome, endereco, dono, tipoCozinha));
        return id;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        validarTipo(tipoEmpresa, "mercado");
        validarNome(nome);
        validarEndereco(endereco);
        validarHorario(abre, fecha);
        validarTipoMercado(tipoMercado);
        validarDonoECadastroDisponivel(dono, nome, endereco);

        int id = repository.gerarId();
        repository.salvar(new Mercado(id, nome, endereco, dono, abre, fecha, tipoMercado));
        return id;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        validarTipo(tipoEmpresa, "farmacia");
        validarNome(nome);
        validarEndereco(endereco);
        validarDonoECadastroDisponivel(dono, nome, endereco);

        int id = repository.gerarId();
        repository.salvar(new Farmacia(id, nome, endereco, dono, aberto24Horas, numeroFuncionarios));
        return id;
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        validarDono(idDono);

        List<Empresa> empresasDoDono = new ArrayList<>();
        for (Empresa empresa : repository.listarTodos()) {
            if (empresa.getDonoId() == idDono) empresasDoDono.add(empresa);
        }

        return formatarEmpresas(empresasDoDono);
    }

    public String getAtributoEmpresa(int empresaId, String atributo) throws Exception {
        Empresa empresa = repository.buscarPorId(empresaId);
        if (empresa == null) throw new EmpresaNaoCadastradaException();

        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoExc();

        String attr = atributo.trim();

        switch (attr) {
            case "nome":     return empresa.getNome();
            case "endereco": return empresa.getEndereco();
            case "tipoCozinha":
                if (empresa instanceof Restaurante) return ((Restaurante) empresa).getTipoCozinha();
                throw new AtributoInvalidoExc();
            case "abre":
                if (empresa instanceof Mercado) return ((Mercado) empresa).getAbre();
                throw new AtributoInvalidoExc();
            case "fecha":
                if (empresa instanceof Mercado) return ((Mercado) empresa).getFecha();
                throw new AtributoInvalidoExc();
            case "tipoMercado":
                if (empresa instanceof Mercado) return ((Mercado) empresa).getTipoMercado();
                throw new AtributoInvalidoExc();
            case "aberto24Horas":
                if (empresa instanceof Farmacia) return String.valueOf(((Farmacia) empresa).isAberto24Horas());
                throw new AtributoInvalidoExc();
            case "numeroFuncionarios":
                if (empresa instanceof Farmacia) return String.valueOf(((Farmacia) empresa).getNumeroFuncionarios());
                throw new AtributoInvalidoExc();
            case "dono":
                Usuario dono = usuarioService.buscarPorId(empresa.getDonoId());
                return dono.getNome();
            default:
                throw new AtributoInvalidoExc();
        }
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
        if (indice < 0) throw new IndiceInvalidoException();

        validarDono(idDono);

        List<Empresa> empresasComMesmoNome = new ArrayList<>();
        for (Empresa empresa : repository.listarTodos()) {
            if (empresa.getDonoId() == idDono && nome.equals(empresa.getNome())) {
                empresasComMesmoNome.add(empresa);
            }
        }

        if (empresasComMesmoNome.isEmpty()) throw new NaoExisteEmpresaComEsseNomeException();
        if (indice >= empresasComMesmoNome.size()) throw new IndiceMaiorQueEsperadoException();

        return empresasComMesmoNome.get(indice).getId();
    }

    public Empresa buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public void alterarFuncionamento(int mercadoId, String abre, String fecha) throws Exception {
        Empresa empresa = repository.buscarPorId(mercadoId);
        if (!(empresa instanceof Mercado)) throw new MercadoInvalidoException();

        validarHorario(abre, fecha);

        Mercado mercado = (Mercado) empresa;
        mercado.setAbre(abre);
        mercado.setFecha(fecha);
    }

    public void cadastrarEntregador(int empresaId, int entregadorId) throws Exception {
        if (!usuarioService.ehEntregador(entregadorId)) throw new UsuarioNaoEntregadorException();

        Empresa empresa = repository.buscarPorId(empresaId);
        List<Integer> entregadores = empresa.getEntregadoresIds();

        if (!entregadores.contains(entregadorId)) {
            entregadores.add(entregadorId);
        }
    }

    public String getEntregadores(int empresaId) throws Exception {
        Empresa empresa = repository.buscarPorId(empresaId);
        List<Integer> entregadores = empresa.getEntregadoresIds();

        if (entregadores.isEmpty()) return "{[]}";

        StringBuilder resultado = new StringBuilder("{[");
        for (int i = 0; i < entregadores.size(); i++) {
            Usuario entregador = usuarioService.buscarPorId(entregadores.get(i));
            resultado.append(entregador.getEmail());
            if (i < entregadores.size() - 1) resultado.append(", ");
        }
        resultado.append("]}");
        return resultado.toString();
    }

    public String getEmpresas(int entregadorId) throws Exception {
        if (!usuarioService.ehEntregador(entregadorId)) throw new UsuarioNaoEntregadorException();

        List<Empresa> empresasDoEntregador = new ArrayList<>();
        for (Empresa empresa : repository.listarTodos()) {
            if (empresa.getEntregadoresIds().contains(entregadorId)) {
                empresasDoEntregador.add(empresa);
            }
        }

        return formatarEmpresas(empresasDoEntregador);
    }

    public boolean entregadorTrabalhaNaEmpresa(int empresaId, int entregadorId) {
        Empresa empresa = repository.buscarPorId(empresaId);
        return empresa != null && empresa.getEntregadoresIds().contains(entregadorId);
    }

    public boolean entregadorTemEmpresa(int entregadorId) {
        for (Empresa empresa : repository.listarTodos()) {
            if (empresa.getEntregadoresIds().contains(entregadorId)) return true;
        }
        return false;
    }

    public boolean ehFarmacia(int empresaId) {
        return repository.buscarPorId(empresaId) instanceof Farmacia;
    }

    private void validarTipo(String tipo, String esperado) throws TipoEmpresaInvalidoException {
        if (tipo == null || tipo.trim().isEmpty() || !tipo.equals(esperado)) {
            throw new TipoEmpresaInvalidoException();
        }
    }

    private void validarNome(String nome) throws NomeInvalidoException {
        if (nome == null || nome.trim().isEmpty()) throw new NomeInvalidoException();
    }

    private void validarEndereco(String endereco) throws EnderecoEmpresaInvalidoException {
        if (endereco == null || endereco.trim().isEmpty()) throw new EnderecoEmpresaInvalidoException();
    }

    private void validarTipoMercado(String tipoMercado) throws TipoMercadoInvalidoException {
        if (tipoMercado == null || tipoMercado.trim().isEmpty()) throw new TipoMercadoInvalidoException();
    }

    private void validarHorario(String abre, String fecha) throws Exception {
        if (abre == null || fecha == null) throw new HorarioInvalidoException();
        if (!abre.matches("\\d{2}:\\d{2}") || !fecha.matches("\\d{2}:\\d{2}")) {
            throw new FormatoHoraInvalidoException();
        }

        int abertura = converterParaMinutos(abre);
        int fechamento = converterParaMinutos(fecha);

        if (abertura >= fechamento) throw new HorarioInvalidoException();
    }

    private int converterParaMinutos(String hora) throws HorarioInvalidoException {
        int horas = Integer.parseInt(hora.substring(0, 2));
        int minutos = Integer.parseInt(hora.substring(3, 5));

        if (horas > 23 || minutos > 59) throw new HorarioInvalidoException();

        return horas * 60 + minutos;
    }

    private void validarDono(int idUsuario) throws UsuarioNaoPodeCriarEmpresaException {
        if (!usuarioService.ehDonoDeEmpresa(idUsuario)) throw new UsuarioNaoPodeCriarEmpresaException();
    }

    private void validarDonoECadastroDisponivel(int dono, String nome, String endereco) throws Exception {
        validarDono(dono);

        boolean nomeELocalRepetidos = false;
        boolean nomeJaExiste = false;

        for (Empresa empresa : repository.listarTodos()) {
            boolean mesmoNome = Objects.equals(empresa.getNome(), nome);
            if (empresa.getDonoId() == dono && mesmoNome && Objects.equals(empresa.getEndereco(), endereco)) {
                nomeELocalRepetidos = true;
            }
            if (empresa.getDonoId() != dono && mesmoNome) {
                nomeJaExiste = true;
            }
        }

        if (nomeELocalRepetidos) throw new EmpresaNomeELocalRepetidosException();
        if (nomeJaExiste) throw new EmpresaComNomeJaExisteException();
    }

    private String formatarEmpresas(List<Empresa> empresas) {
        if (empresas.isEmpty()) return "{[]}";

        StringBuilder resultado = new StringBuilder("{[");
        for (int i = 0; i < empresas.size(); i++) {
            Empresa empresa = empresas.get(i);
            resultado.append("[").append(empresa.getNome()).append(", ").append(empresa.getEndereco()).append("]");
            if (i < empresas.size() - 1) resultado.append(", ");
        }
        resultado.append("]}");
        return resultado.toString();
    }
}
