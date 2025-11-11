# Questão 3: DI substituindo Padrões Criacionais do GoF

## Estrutura

```
q3/di/
├── before/              # Padrões criacionais clássicos (criticados)
│   ├── SingletonDatabase.java
│   ├── ServiceAbstractFactory.java
│   ├── PrototypeReport.java
│   └── BeforeDemo.java
└── after/               # Dependency Injection moderna
    ├── Logger.java
    ├── ConsoleLogger.java
    ├── Database.java
    ├── InMemoryDatabase.java
    ├── SalesReport.java
    ├── OrderService.java
    ├── AppConfig.java
    └── DIDemo.java
```

## Os 3 Padrões Criticados

### 1) Singleton

**Before:** [`SingletonDatabase.java`](before/SingletonDatabase.java)

- Acesso global via `getInstance()`
- Problemas:
  - Estado global compartilhado
  - Dificulta testes (impossível injetar mock)
  - Acoplamento forte

**After:** DI com interfaces

```java
// Cada serviço recebe SUA instância
Database db = new InMemoryDatabase();
OrderService service = new OrderService(db, ...);
```

### 2) Abstract Factory

**Before:** [`ServiceAbstractFactory.java`](before/ServiceAbstractFactory.java)

- Precisa de:
  - 1 interface `ServiceAbstractFactory`
  - 2 factories concretas (`ProductionFactory`, `TestFactory`)
  - 2 interfaces de produtos (`DatabaseConnection`, `NotificationService`)
  - 4 produtos concretos (`PostgresConnection`, `InMemoryConnection`, etc.)
  - **Total: 9 artefatos** para criar 2 objetos!

**After:** AppConfig (wiring centralizado)

```java
// 1 classe, métodos simples
public class AppConfig {
    public OrderService orderService() {
        return new OrderService(new InMemoryDatabase(), new ConsoleLogger());
    }
}
```

### 3) Prototype

**Before:** [`PrototypeReport.java`](before/PrototypeReport.java)

- `clone()` para duplicar objetos
- Problemas:
  - Shallow copy por padrão (objetos aninhados compartilhados)
  - Frágil com objetos complexos
  - Pouco usado em linguagens modernas com GC

**After:** Supplier<T>

```java
// Gera novas instâncias sob demanda
Supplier<SalesReport> factory = () -> new SalesReport("Monthly");
SalesReport r1 = factory.get();
SalesReport r2 = factory.get();
```

## Como Executar

### Before (padrões criticados)

```bash
mvn compile
java -cp target/classes com.example.patterns.q3.di.before.BeforeDemo
```

Saída esperada:

```
Q3 - ANTES (padrões criacionais clássicos)

1) SINGLETON - acesso global, dificulta testes
[SingletonDB] salvando: order=001
[SingletonDB] salvando: order=002

2) ABSTRACT FACTORY - verbosidade excessiva
[PostgreSQL] Executando: INSERT INTO orders VALUES ('ORD-100')
[Email] Enviando: Pedido ORD-100 criado
[InMemory] Executando: INSERT INTO orders VALUES ('TEST-999')
[Mock] Simulando envio: Pedido TEST-999 criado

3) PROTOTYPE - clone() frágil e pouco prático
Original: Relatório de Vendas
Clone: Relatório de Vendas
```

### After (Dependency Injection)

```bash
java -cp target/classes com.example.patterns.q3.di.after.DIDemo
```

Saída esperada:

```
Q3 - DEPOIS (Dependency Injection)

1) DI substitui SINGLETON - sem estado global
[InMemoryDB] data-1
[InMemoryDB] data-2

2) DI substitui ABSTRACT FACTORY - sem verbosidade
[LOG] Colocando pedido ORD-100
[InMemoryDB] order=ORD-100
[MOCK-DB] order=TEST-999

3) DI substitui PROTOTYPE - sem clone()
Report 1: Relatório Mensal
Report 2: Relatório Mensal

=== VANTAGENS DA DI ===
✓ Testabilidade: injeta mocks/fakes facilmente
✓ Desacoplamento: depende de interfaces, não classes concretas
✓ Simplicidade: sem explosão de factories/classes
✓ Flexibilidade: troca implementações sem recompilar
✓ Manutenibilidade: wiring centralizado (AppConfig ou @Bean)
```

## Comparação Direta

| Aspecto              | Before (Padrões Clássicos)                     | After (DI)                           |
| -------------------- | ---------------------------------------------- | ------------------------------------ |
| **Singleton**        | `getInstance()` retorna sempre mesma instância | Cada serviço recebe SUA instância    |
| **Testabilidade**    | Difícil (estado global, mock por herança)      | Fácil (injeta mocks via construtor)  |
| **Abstract Factory** | 9 classes para criar 2 objetos                 | 1 AppConfig com métodos simples      |
| **Prototype**        | `clone()` frágil e shallow                     | `Supplier<T>` type-safe              |
| **Acoplamento**      | Classes conhecem implementações concretas      | Dependem de interfaces               |
| **Verbosidade**      | Alta (muitas classes/interfaces)               | Baixa (wiring centralizado)          |
| **Frameworks**       | Manual, boilerplate repetitivo                 | Spring/Guice automatizam com @Inject |

## Vantagens da DI Explicitadas no Código

### 1. Testabilidade

```java
// DIDemo.java linha 34-37
Logger mockLogger = msg -> { /* silencioso */ };
Database mockDb = data -> System.out.println("[MOCK-DB] " + data);
OrderService testService = new OrderService(mockLogger, mockDb, ...);
// ↑ Injeta mocks SEM criar TestFactory, TestDatabase, etc.
```

### 2. Sem Estado Global

```java
// DIDemo.java linha 19-23
Database db1 = new InMemoryDatabase();
Database db2 = new InMemoryDatabase();
// ↑ Instâncias independentes, testáveis isoladamente
```

### 3. Simplicidade vs Abstract Factory

```java
// Before: 9 classes (ServiceAbstractFactory.java)
ServiceAbstractFactory factory = new ProductionFactory();
DatabaseConnection db = factory.createDatabase();
NotificationService notifier = factory.createNotifier();
OrderServiceLegacy service = new OrderServiceLegacy(db, notifier);

// After: 1 linha (DIDemo.java linha 29)
OrderService service = cfg.orderService();
```

### 4. Supplier<T> vs clone()

```java
// DIDemo.java linha 43-47
Supplier<SalesReport> factory = () -> new SalesReport("Mensal");
SalesReport r1 = factory.get();
SalesReport r2 = factory.get();
// ↑ Limpo, type-safe, sem clone() problemático
```

## Conclusão

Os padrões criacionais do GoF (1994) eram necessários porque:

- Linguagens não tinham frameworks de DI
- Criação de objetos era caótica

Hoje (2025), DI moderna:

- **Elimina Singleton**: sem estado global, testável
- **Substitui Abstract Factory**: sem verbosidade, wiring centralizado
- **Remove Prototype**: Supplier<T> é superior a clone()

**Resultado:** código mais simples, testável e manutenível.
