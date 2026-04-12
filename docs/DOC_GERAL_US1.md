# Correção US1


### US1_1
Principais ajustes realizados:
- Inclusao do metodo `encerrarSistema` na facade
- Ajuste do `getAtributoUsuario` para atributos esperados nos testes
- Padronizacao das mensagens de excecao para casar com o script
- Validacoes de campos obrigatorios na ordem correta
- Correcao do fluxo de login para lancar excecao quando invalido

Resultado validado:
- `us1_1.txt` com 32 testes OK

### US1_2
Principais ajustes realizados:
- Inicializacao do `userManager` no construtor da facade
- Serializacao da entidade `Usuario`
- Persistencia da lista de usuarios em arquivo
- Carregamento dos dados ao iniciar o sistema
- Salvamento dos dados ao encerrar o sistema

Resultado validado:
- `us1_2.txt` com 11 testes OK

## Arquivos principais alterados
- MyFood2026.1/src/br/ufal/ic/myfood/Facade.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/UsuarioManager.java
- MyFood2026.1/src/br/ufal/ic/myfood/models/Usuario.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/NomeInvalidoException.java
- MyFood2026.1/src/br/ufal/ic/myfood/exceptions/EmailInvalidoException.java
