# Rotina de transações

Cada portador de cartão (cliente) possui uma conta com seus dados.
A cada operação realizada pelo cliente uma transação é criada e associada à sua
respectiva conta.
Cada transação possui um tipo (compra a vista, compra parcelada, saque ou pagamento),
um valor e uma data de criação.
Transações de tipo ​ compra e saque são registradas com ​ valor negativo​ , enquanto
transações de ​ pagamento​ são registradas com ​ valor positivo​ .


# Requisitos

* Java 11+;
* Docker

# Biblioteca e Estrutura

* Aplicação SpringBoot
* Hibernate
* Spring Validator
* Jackson
* Banco H2 para testes de mapeamento e repositories
* Banco Postgres via docker em runtime
* Swagger para documentação

# Tasks 

## Iniciar app:

* ./gradlew startApp

## Rodar testes:

* ./gradlew check 
* tests report ./build/reports/tests/test/index.html
* coverage report ./build/reports/jacoco/test/html/index.html

## Executar app via docker:

# TODO

# Endpoints  [swagger](http://localhost:8080/swagger-ui/)
