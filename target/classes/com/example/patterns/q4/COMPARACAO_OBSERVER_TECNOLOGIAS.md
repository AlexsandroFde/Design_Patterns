# Questão 4: Observer Pattern nas Tecnologias Modernas

## Comparação: Classic Observer vs 3 Tecnologias

Este documento compara o Observer clássico com 3 implementações modernas:

1. **Pub/Sub** (Publish-Subscribe com Broker)
2. **Java Flow API** (Reactive Streams)
3. **EventEmitter** (Node.js style)

---

## 1. Observer Clássico (GoF 1994)

### Código:

```java
// Subject mantém lista de observers
ConcreteSubject subject = new ConcreteSubject();

// Cria observers
ConcreteObserver observerA = new ConcreteObserver("A");
ConcreteObserver observerB = new ConcreteObserver("B");

// Registra observers
subject.register(observerA);
subject.register(observerB);

// Muda estado → notifica todos
subject.changeState("Primeiro evento");
```

### Características:

- ✅ **Direto:** Subject conhece e notifica Observers diretamente
- ✅ **Simples:** Relação 1:N clara
- ❌ **Acoplado:** Subject tem referência aos Observers
- ❌ **Síncrono:** Notificação bloqueia até todos processarem
- ❌ **Sem filtros:** Todos recebem tudo

### Estrutura:

```
Subject (1)  ──┬──→ Observer A
               ├──→ Observer B
               └──→ Observer C
```

---

## 2. Pub/Sub (Publish-Subscribe)

### Código:

```java
Broker broker = new Broker();  // Intermediário

// Subscribers se inscrevem em TÓPICOS
Subscriber subA = (topic, msg) -> System.out.println("A: " + msg);
Subscriber subB = (topic, msg) -> System.out.println("B: " + msg);

broker.subscribe("orders", subA);
broker.subscribe("payments", subA);
broker.subscribe("orders", subB);

// Publisher publica em tópico (não conhece subscribers!)
broker.publish("orders", "pedido #123");
broker.publish("payments", "pago #123");
```

### Características:

- ✅ **Desacoplado:** Publisher e Subscriber não se conhecem
- ✅ **Filtros:** Subscribers escolhem tópicos de interesse
- ✅ **Escalável:** Broker pode ser distribuído (RabbitMQ, Kafka)
- ✅ **Assíncrono:** Mensagens podem ser enfileiradas
- ❌ **Complexidade:** Precisa de broker/middleware

### Estrutura:

```
Publisher  ───→  [BROKER]  ───┬──→ Subscriber A (topic: orders)
                              ├──→ Subscriber B (topic: orders)
                              └──→ Subscriber C (topic: payments)
```

### Comparação com Observer Clássico:

| Aspecto            | Classic Observer      | Pub/Sub                    |
| ------------------ | --------------------- | -------------------------- |
| **Acoplamento**    | Subject → Observers   | Nenhum (via Broker)        |
| **Filtros**        | Não                   | Sim (tópicos)              |
| **Escalabilidade** | Limitada (1 processo) | Alta (distribuído)         |
| **Persistência**   | Não                   | Possível (broker armazena) |
| **Exemplos reais** | UI listeners          | Kafka, RabbitMQ, AWS SNS   |

---

## 3. Java Flow API (Reactive Streams)

### Código:

```java
// Publisher (equivalente a Subject)
SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

// Subscribers (equivalente a Observers)
Flow.Subscriber<String> subA = new PrintSubscriber("A");
Flow.Subscriber<String> subB = new PrintSubscriber("B");

publisher.subscribe(subA);
publisher.subscribe(subB);

// Publica eventos
publisher.submit("Primeiro evento");
publisher.submit("Segundo evento");

publisher.close();  // Sinaliza fim do stream
```

### Características:

- ✅ **Backpressure:** Subscriber controla ritmo (`request(n)`)
- ✅ **Assíncrono:** Não bloqueia o publisher
- ✅ **Protocolo:** `onSubscribe()`, `onNext()`, `onError()`, `onComplete()`
- ✅ **Composição:** Transformações de stream (map, filter)
- ❌ **Complexo:** Mais verboso que Observer clássico

### Estrutura:

```
Publisher  ───→  Subscriber A  (request(1) → recebe 1 item)
           ───→  Subscriber B  (request(10) → recebe 10 itens)
```

### Comparação com Observer Clássico:

| Aspecto               | Classic Observer | Flow API                                          |
| --------------------- | ---------------- | ------------------------------------------------- |
| **Controle de fluxo** | Não (push)       | Sim (pull + push)                                 |
| **Assíncrono**        | Não              | Sim                                               |
| **Protocolo**         | `update()`       | 4 métodos (onSubscribe/onNext/onError/onComplete) |
| **Streams**           | Não              | Sim (coleções assíncronas)                        |
| **Exemplos reais**    | Eventos simples  | Spring WebFlux, RxJava, Reactor                   |

---

## 4. EventEmitter (Node.js style)

### Código:

```java
EventEmitter emitter = new EventEmitter();

// Registra listeners por nome de evento
emitter.on("data", data -> System.out.println("Listener 1: " + data));
emitter.on("data", data -> System.out.println("Listener 2: " + data));

// Listener que executa apenas uma vez
emitter.once("connection", data -> System.out.println("Connected: " + data));

// Emite eventos
emitter.emit("data", "Primeiro evento");
emitter.emit("connection", "Client #1");
emitter.emit("connection", "Client #2");  // Não executa (once)
```

### Características:

- ✅ **Simples:** API minimalista (`on`, `emit`, `off`, `once`)
- ✅ **Nomes de eventos:** Organização por strings (como tópicos)
- ✅ **Flexível:** Listeners podem ser removidos dinamicamente
- ✅ **Once:** Listeners descartáveis
- ❌ **Limitado:** Síncrono, sem backpressure

### Estrutura:

```
EventEmitter
  ├─ "data" event  ──┬──→ Listener 1
  │                  └──→ Listener 2
  └─ "connection"  ────→ Listener 3 (once)
```

### Comparação com Observer Clássico:

| Aspecto              | Classic Observer         | EventEmitter                  |
| -------------------- | ------------------------ | ----------------------------- |
| **API**              | `register()`, `notify()` | `on()`, `emit()`, `off()`     |
| **Eventos nomeados** | Não                      | Sim (strings)                 |
| **Once**             | Não                      | Sim (`once()`)                |
| **Linguagem**        | Java idiomático          | JavaScript idiomático         |
| **Exemplos reais**   | Java Swing               | Node.js (HTTP, Streams, etc.) |

---

## Comparação Geral: As 4 Abordagens

| Característica   | Classic  | Pub/Sub        | Flow API         | EventEmitter |
| ---------------- | -------- | -------------- | ---------------- | ------------ |
| **Acoplamento**  | Alto     | Baixo          | Médio            | Médio        |
| **Assíncrono**   | ❌       | ✅             | ✅               | ❌           |
| **Backpressure** | ❌       | ⚠️             | ✅               | ❌           |
| **Filtros**      | ❌       | ✅ (tópicos)   | ⚠️               | ✅ (eventos) |
| **Distribuído**  | ❌       | ✅             | ❌               | ❌           |
| **Complexidade** | Baixa    | Alta           | Alta             | Baixa        |
| **Uso ideal**    | UI local | Microsserviços | Streams reativos | Event loops  |

---

## Evolução Conceitual

```
Observer Clássico (1994)
    ↓
EventEmitter (2009 - Node.js)
    ↓
Pub/Sub com Broker (2011 - RabbitMQ/Kafka)
    ↓
Reactive Streams (2013 - RxJava)
    ↓
Flow API (2017 - Java 9)
    ↓
Arquiteturas Event-Driven (2020+)
```

### Raiz Comum:

Todos compartilham o mesmo conceito:

- **Observer:** "Me avise quando algo acontecer"
- **EventEmitter:** Observer com eventos nomeados
- **Pub/Sub:** Observer desacoplado com broker
- **Flow API:** Observer assíncrono com backpressure

---

## Quando usar cada um?

### Classic Observer

- ✅ UI local (botões, listeners)
- ✅ Lógica simples, síncrona
- ✅ Poucos observers (< 10)

### Pub/Sub

- ✅ Microsserviços comunicando
- ✅ Sistema distribuído
- ✅ Precisa de persistência/filas
- ✅ Milhares de subscribers

### Flow API

- ✅ Streams de dados assíncronos
- ✅ Precisa controlar velocidade (backpressure)
- ✅ Processamento de grandes volumes
- ✅ Composição de operações (map/filter)

### EventEmitter

- ✅ Event loop (Node.js, JavaScript)
- ✅ API simples para múltiplos eventos
- ✅ Listeners descartáveis (`once`)
- ✅ Código idiomático JavaScript/TypeScript

---

## Exemplo Prático: Sistema de Pedidos

### Classic Observer:

```java
OrderService service = new OrderService();
service.addListener(new EmailNotifier());
service.addListener(new SMSNotifier());
service.createOrder("ORD-123");  // Notifica ambos
```

### Pub/Sub:

```java
broker.subscribe("order.created", new EmailNotifier());
broker.subscribe("order.created", new SMSNotifier());
broker.publish("order.created", orderData);
```

### Flow API:

```java
orderPublisher.subscribe(new EmailSubscriber());
orderPublisher.subscribe(new SMSSubscriber());
orderPublisher.submit(orderData);
```

### EventEmitter:

```java
emitter.on("order.created", order -> sendEmail(order));
emitter.on("order.created", order -> sendSMS(order));
emitter.emit("order.created", orderData);
```

---

## Conclusão

**Observer Pattern é a raiz de tudo:**

- **EventEmitter** = Observer + eventos nomeados
- **Pub/Sub** = Observer + broker + tópicos
- **Flow API** = Observer + assíncrono + backpressure

Todos resolvem o mesmo problema (1:N notification), mas com diferentes trade-offs de complexidade, acoplamento e features! 🎯
