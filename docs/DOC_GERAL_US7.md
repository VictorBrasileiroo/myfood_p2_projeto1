# Correcao US7

### US7_1
Principais ajustes realizados:
- Criacao da entidade `Entregador` como especializacao de `Usuario`
- Inclusao do overload de `criarUsuario` para entregadores na facade
- Implementacao da criacao de entregadores no `UsuarioService`
- Validacao de veiculo e placa no cadastro de entregadores
- Validacao de placa duplicada entre entregadores
- Inclusao dos atributos `veiculo` e `placa` em `getAtributoUsuario`
- Inclusao do metodo `cadastrarEntregador` na facade
- Vinculo de entregadores diretamente na empresa por lista de ids
- Inclusao dos metodos `getEntregadores` e `getEmpresas`
- Validacao para impedir usuario que nao e entregador nos vinculos e consultas

Resultado validado:
- `us7_1.txt` com 42 testes OK

### US7_2
Principais ajustes realizados:
- Persistencia dos entregadores no mesmo arquivo XML de usuarios
- Persistencia dos vinculos entre entregadores e empresas no XML de empresas
- Carregamento correto dos atributos `veiculo` e `placa` apos reinicio
- Manutencao das consultas de entregadores e empresas apos reinicio

Resultado validado:
- `us7_2.txt` com 16 testes OK

## Arquivos principais criados/alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Entregador.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Empresa.java
- MyFood2026.1/src/br/ufal/ic/myfood/repositories/UsuarioRepository.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/UsuarioService.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/EmpresaService.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/VeiculoInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/PlacaInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/UsuarioNaoEntregadorException.java
