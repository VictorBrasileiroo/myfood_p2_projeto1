# Correcao US2


### US2_1
Principais ajustes realizados:
- Inclusao dos metodos `criarEmpresa`, `getEmpresasDoUsuario`, `getAtributoEmpresa` e `getIdEmpresa` na facade
- Criacao da hierarquia de empresas (`Empresa` abstrata e `Restaurante`)
- Criacao do `EmpresaManager` com lista em memoria e geracao sequencial de id
- Implementacao da ordem de validacao exigida nos testes de criacao, consulta de atributo e busca por indice
- Padronizacao das mensagens de excecao para casar com o script EasyAccept
- Ajuste do formato de retorno de `getEmpresasDoUsuario` para comparacao textual exata

Resultado validado:
- `us2_1.txt` com 35 testes OK

### US2_2
Principais ajustes realizados:
- Persistencia de empresas com XML (`dados/empresas.xml`) incluindo `proximoId` e lista de empresas
- Carregamento dos dados de empresas no construtor da facade
- Salvamento dos dados de empresas no metodo `encerrarSistema`
- Manutencao da compatibilidade do `getAtributoEmpresa` e `getEmpresasDoUsuario` apos reinicio do sistema

Resultado validado:
- `us2_2.txt` com 13 testes OK

## Arquivos principais alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Empresa.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Restaurante.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/EmpresaManager.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EmpresaComNomeJaExisteException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EmpresaNomeELocalRepetidosException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/UsuarioNaoPodeCriarEmpresaException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EmpresaNaoCadastradaException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/NaoExisteEmpresaComEsseNomeException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/IndiceMaiorQueEsperadoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/IndiceInvalidoException.java