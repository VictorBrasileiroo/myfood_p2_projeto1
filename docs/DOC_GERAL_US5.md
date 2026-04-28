# Correcao US5

### US5_1
Principais ajustes realizados:
- Criacao da entidade `Mercado` como especializacao de `Empresa`
- Inclusao do overload de `criarEmpresa` para mercados na facade
- Implementacao da criacao de mercados no `EmpresaService`
- Validacao de tipo de empresa, nome, endereco, horario e tipo de mercado
- Validacao de formato de horario no padrao `HH:MM`
- Validacao para impedir abertura maior ou igual ao fechamento
- Manutencao das regras de nome repetido e nome/local repetidos entre empresas
- Inclusao dos atributos `abre`, `fecha` e `tipoMercado` em `getAtributoEmpresa`
- Inclusao do metodo `alterarFuncionamento` para mercados
- Validacao para impedir alterar funcionamento de empresas que nao sao mercados
- Manutencao do fluxo de produtos e pedidos funcionando para mercados

Resultado validado:
- `us5_1.txt` com 122 testes OK

### US5_2
Principais ajustes realizados:
- Persistencia de mercados no mesmo arquivo XML de empresas
- Carregamento correto dos dados especificos de mercado apos reinicio
- Manutencao dos horarios alterados por `alterarFuncionamento`
- Manutencao dos produtos e pedidos vinculados a mercados apos reinicio

Resultado validado:
- `us5_2.txt` com 32 testes OK

## Arquivos principais criados/alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Mercado.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/EmpresaService.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/TipoEmpresaInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EnderecoEmpresaInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/FormatoHoraInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/HorarioInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/TipoMercadoInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/MercadoInvalidoException.java
