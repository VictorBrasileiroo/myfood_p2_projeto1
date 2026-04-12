# Correcao - US1_1

## Correcoes aplicadas

### 1) Mensagens de excecao padronizadas
As mensagens foram ajustadas para bater exatamente com o esperado no arquivo de teste:
- Nome invalido
- Email invalido

### 2) Separacao dos fluxos de criacao de usuario
Foi separado o fluxo de criacao em dois caminhos:
- Criacao com 4 parametros (cliente)
- Criacao com 5 parametros (dono com CPF)

### 3) Login com falha agora lanca excecao
No metodo de login, quando email/senha nao conferem, o sistema agora lanca `LoginSenhaInvalidosException`.
Antes, o metodo retornava `null`, o que nao atendia o teste.

## Arquivos alterados
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/NomeInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EmailInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/UsuarioManager.java

