Demonstracao dos padrões (PT-BR)

Este pequeno projeto Java demonstra as diferenças conceituais e práticas entre
três padrões relacionados à persistência e ao domínio:

- DAO (Data Access Object): abstração para acesso a dados (CRUD). Focado em
  operações de acesso/persistência.
- Data Mapper (Martin Fowler): separa a representação de persistência (DTOs)
  do modelo de domínio; o mapper converte entre DTOs e objetos de domínio.
- Repository (Domain-Driven Design): trabalha com agregados (aggregate roots),
  recuperando e persistindo o agregado como uma unidade e reforçando
  invariantes/consistência.

Saída esperada: mensagens no console mostrando quantos pedidos foram lidos
por cada abordagem e demonstração da invariante do agregado (não é possível
adicionar mais de 10 itens a um pedido).

Notas:

- O projeto usa Jackson para desserializar `src/main/resources/data/orders.json`.
- A persistência real foi simulada por mensagens no console; a ideia é focar na
  separação de responsabilidades entre DAO, Data Mapper e Repository.

## Onde estão as questões

- Questão 1 (já implementada):

  - Código principal em `src/main/java/com/example/patterns` (DAO, Data Mapper, Repository) e `Main.java`.

- Questão 2 (Refactoring to Patterns):

  - Strategy (Replace Conditional with Polymorphism)
    - Antes: `com.example.patterns.q2.strategy.before.FreightCalculatorBefore`
    - Depois: `com.example.patterns.q2.strategy.after.*` e demo `com.example.patterns.q2.strategy.StrategyDemo`
  - Decorator (Move Embellishment to Decorator)
    - Antes: `com.example.patterns.q2.decorator.before.NotifierBefore`
    - Depois: `com.example.patterns.q2.decorator.after.*` e demo `com.example.patterns.q2.decorator.DecoratorDemo`

- Questão 3 (DI substituindo padrões criacionais do GoF):
  - Antes: `com.example.patterns.q3.di.before.*` e demo `com.example.patterns.q3.di.before.BeforeDemo`
  - Depois (com DI): `com.example.patterns.q3.di.after.*` e demo `com.example.patterns.q3.di.after.DIDemo`

## Como executar

Este é um projeto Maven (Java 11). As classes abaixo têm `public static void main`:

- Q1: `com.example.patterns.Main`
- Q2 Strategy: `com.example.patterns.q2.strategy.StrategyDemo`
- Q2 Decorator: `com.example.patterns.q2.decorator.DecoratorDemo`
- Q3 Antes: `com.example.patterns.q3.di.before.BeforeDemo`
- Q3 Depois (DI): `com.example.patterns.q3.di.after.DIDemo`

Os códigos possuem comentários indicando claramente a qual questão pertencem e o que estão demonstrando.
