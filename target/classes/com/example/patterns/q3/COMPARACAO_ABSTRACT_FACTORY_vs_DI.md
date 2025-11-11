# Comparação: Abstract Factory vs Dependency Injection

## Problema: Criar OrderService com diferentes dependências para Produção e Teste

---

## BEFORE: Abstract Factory (verboso)

### Código necessário:

#### 1. Interface da Factory

```java
public interface ServiceAbstractFactory {
    DatabaseConnection createDatabase();
    NotificationService createNotifier();
}
```

#### 2. Factory de Produção

```java
class ProductionFactory implements ServiceAbstractFactory {
    @Override
    public DatabaseConnection createDatabase() {
        return new PostgresConnection();
    }

    @Override
    public NotificationService createNotifier() {
        return new EmailNotificationService();
    }
}
```

#### 3. Factory de Teste

```java
class TestFactory implements ServiceAbstractFactory {
    @Override
    public DatabaseConnection createDatabase() {
        return new InMemoryConnection();
    }

    @Override
    public NotificationService createNotifier() {
        return new MockNotificationService();
    }
}
```

#### 4. Interfaces dos Produtos

```java
interface DatabaseConnection {
    void execute(String sql);
}

interface NotificationService {
    void notify(String message);
}
```

#### 5. Implementações de Produção

```java
class PostgresConnection implements DatabaseConnection {
    @Override
    public void execute(String sql) {
        System.out.println("[PostgreSQL] Executando: " + sql);
    }
}

class EmailNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("[Email] Enviando: " + message);
    }
}
```

#### 6. Implementações de Teste

```java
class InMemoryConnection implements DatabaseConnection {
    @Override
    public void execute(String sql) {
        System.out.println("[InMemory] Executando: " + sql);
    }
}

class MockNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("[Mock] Simulando envio: " + message);
    }
}
```

#### 7. Serviço que usa os produtos

```java
class OrderServiceLegacy {
    private final DatabaseConnection db;
    private final NotificationService notifier;

    public OrderServiceLegacy(DatabaseConnection db, NotificationService notifier) {
        this.db = db;
        this.notifier = notifier;
    }

    public void placeOrder(String orderId) {
        db.execute("INSERT INTO orders VALUES ('" + orderId + "')");
        notifier.notify("Pedido " + orderId + " criado");
    }
}
```

### Uso:

```java
// Produção
ServiceAbstractFactory prodFactory = new ProductionFactory();
DatabaseConnection db = prodFactory.createDatabase();
NotificationService notifier = prodFactory.createNotifier();
OrderServiceLegacy service = new OrderServiceLegacy(db, notifier);
service.placeOrder("ORD-100");

// Teste
ServiceAbstractFactory testFactory = new TestFactory();
DatabaseConnection testDb = testFactory.createDatabase();
NotificationService testNotifier = testFactory.createNotifier();
OrderServiceLegacy testService = new OrderServiceLegacy(testDb, testNotifier);
testService.placeOrder("TEST-999");
```

### Contagem:

- **1** interface de factory
- **2** factories concretas
- **2** interfaces de produtos
- **4** implementações de produtos
- **1** classe de serviço
- **Total: 10 artefatos**

---

## AFTER: Dependency Injection (simples)

### Código necessário:

#### 1. Interfaces (mesmas do before, mas VOCÊ ESCOLHE usá-las ou não)

```java
public interface Database {
    void save(String data);
}

public interface Logger {
    void info(String msg);
}
```

#### 2. Implementações

```java
public class InMemoryDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("[InMemoryDB] " + data);
    }
}

public class ConsoleLogger implements Logger {
    @Override
    public void info(String msg) {
        System.out.println("[LOG] " + msg);
    }
}
```

#### 3. Serviço com injeção via construtor

```java
public class OrderService {
    private final Logger logger;
    private final Database database;

    public OrderService(Logger logger, Database database) {
        this.logger = logger;
        this.database = database;
    }

    public void place(String orderId) {
        logger.info("Colocando pedido " + orderId);
        database.save("order=" + orderId);
    }
}
```

#### 4. AppConfig (opcional, simula container DI)

```java
public class AppConfig {
    public static OrderService createOrderService() {
        return new OrderService(
            new ConsoleLogger(),
            new InMemoryDatabase()
        );
    }
}
```

### Uso:

```java
// Produção (via AppConfig)
OrderService prodService = AppConfig.createOrderService();
prodService.place("ORD-100");

// Teste (injeta mocks diretamente)
Logger mockLogger = msg -> { /* silencioso */ };
Database mockDb = data -> System.out.println("[MOCK] " + data);
OrderService testService = new OrderService(mockLogger, mockDb);
testService.place("TEST-999");
```

### Contagem:

- **2** interfaces (opcionais, mas recomendadas)
- **2** implementações concretas
- **1** classe de serviço
- **1** AppConfig (opcional)
- **Total: 6 artefatos** (vs 10 do Abstract Factory)

---

## Comparação Direta

| Aspecto                             | Abstract Factory                            | Dependency Injection                             |
| ----------------------------------- | ------------------------------------------- | ------------------------------------------------ |
| **Artefatos necessários**           | 10 classes/interfaces                       | 6 classes/interfaces                             |
| **Para adicionar nova dependência** | +3 classes (interface + 2 impls na factory) | +2 classes (interface + impl)                    |
| **Trocar dependência para teste**   | Criar nova Factory                          | 1 linha (injeta mock)                            |
| **Boilerplate**                     | Alto (factories aninhadas)                  | Baixo (construtor simples)                       |
| **Flexibilidade runtime**           | Limitada (factories pré-definidas)          | Total (qualquer objeto que implemente interface) |
| **Frameworks automatizam?**         | Não                                         | Sim (Spring @Bean/@Inject)                       |
| **Testabilidade**                   | Média (precisa factory de teste)            | Alta (injeta mocks diretamente)                  |
| **Legibilidade**                    | Baixa (muitas indireções)                   | Alta (fluxo direto)                              |

---

## Por que Abstract Factory caiu em desuso?

### 1. **Verbosidade desnecessária**

Para trocar 1 dependência em teste, Abstract Factory exige:

- Criar nova factory
- Implementar TODOS os métodos (mesmo que só precise trocar 1)
- Manter hierarquia sincronizada

Com DI: apenas injeta o mock.

### 2. **Explosão combinatória**

Imagine 3 dependências (DB, Logger, Cache) x 2 ambientes (Prod, Test):

- Abstract Factory: 2^3 = **8 factories** (ou factories com flags = volta aos ifs!)
- DI: **1 AppConfig** com métodos simples

### 3. **Frameworks modernos**

Spring/Guice fazem o wiring automaticamente:

```java
@Configuration
public class AppConfig {
    @Bean
    public OrderService orderService(Logger logger, Database db) {
        return new OrderService(logger, db);
    }
}
```

Sem nenhuma factory manual!

---

## Conclusão

**Abstract Factory era útil em 1994** quando:

- Não havia frameworks de DI
- Criar objetos era caótico
- Linguagens não tinham lambda/interfaces funcionais

**Hoje (2025), DI é superior** porque:

- ✅ Menos código (6 vs 10 artefatos)
- ✅ Mais testável (injeta mocks sem factories extras)
- ✅ Mais flexível (qualquer objeto compatível)
- ✅ Frameworks automatizam (@Bean/@Inject)
- ✅ Mais legível (sem indireções desnecessárias)

**Resultado:** Abstract Factory virou overengineering; DI resolve o mesmo problema de forma mais simples e elegante.
