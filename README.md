
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

Como executar (ambiente com Maven instalado):

Abra um PowerShell e rode:

```powershell
cd C:\Users\Alexsandro\Documents\PP
mvn -DskipTests=true compile exec:java -Dexec.mainClass=com.example.patterns.Main
```

Saída esperada: mensagens no console mostrando quantos pedidos foram lidos
por cada abordagem e demonstração da invariante do agregado (não é possível
adicionar mais de 10 itens a um pedido).

Notas:
- O projeto usa Jackson para desserializar `src/main/resources/data/orders.json`.
- A persistência real foi simulada por mensagens no console; a ideia é focar na
	separação de responsabilidades entre DAO, Data Mapper e Repository.
