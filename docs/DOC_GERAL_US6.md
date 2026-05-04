# Correcao US6

### US6_1
Principais ajustes realizados:
- Criacao da entidade `Farmacia` como especializacao de `Empresa`
- Inclusao do overload de `criarEmpresa` para farmacias na facade
- Implementacao da criacao de farmacias no `EmpresaService`
- Validacao de tipo de empresa, nome, endereco e dono da empresa
- Manutencao das regras de empresa repetida por nome/local e nome repetido com outro dono
- Inclusao dos atributos `aberto24Horas` e `numeroFuncionarios` em `getAtributoEmpresa`
- Manutencao do fluxo de produtos e pedidos funcionando para farmacias

Resultado validado:
- `us6_1.txt` com 84 testes OK

### US6_2
Principais ajustes realizados:
- Persistencia de farmacias no mesmo arquivo XML de empresas
- Carregamento correto dos dados especificos de farmacia apos reinicio
- Manutencao dos produtos e pedidos vinculados a farmacias apos reinicio

Resultado validado:
- `us6_2.txt` com 27 testes OK

## Arquivos principais criados/alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Farmacia.java
- MyFood2026.1/src/br/ufal/ic/myfood/services/EmpresaService.java
