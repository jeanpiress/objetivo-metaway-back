# Instruções de Uso - ObjetivoBackMetaWay

**ObjetivoBackMetaWay** é um sistema de gerenciamento para pet shops, desenvolvido com Java 17 e Spring, utilizando MySQL como banco de dados.  
O sistema é configurado para ser facilmente dockerizado com docker-compose já configurado, e o usuário não precisa ter o Java 17 ou Maven instalados localmente.  
As credenciais de acesso ao banco de dados estão configuradas no arquivo `application.properties`.

## Banco de Dados
O banco de dados é preenchido automaticamente utilizando Flyway, que é executado na inicialização do sistema para aplicar as migrações necessárias.

## Acesso via Swagger
O sistema está configurado com Swagger para facilitar o acesso e visualização das APIs. Para acessar a interface Swagger, use a URL:

[http://localhost:8080/swagger-ui/index.htm](http://localhost:8080/swagger-ui/index.htm)

## Testes de Acesso
Para realizar testes no sistema com os diferentes perfis de usuário, utilize as seguintes credenciais:

### Admin:
- **Usuário**: 12345678900  
- **Senha**: 123456  

### Cliente:
- **Usuário**: 98765432100  
- **Senha**: 123456

