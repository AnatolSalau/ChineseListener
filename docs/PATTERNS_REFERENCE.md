# Patterns & principles reference — refactor catalog

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

**Purpose:** A single **name + short description** catalog for humans and agents during refactors. It does not replace [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) (project rules). Use this doc to **pick the right pattern name** and intent; apply it strictly via **[REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md)** (Phases 0–7). Quick maps and Gradle commands: [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md).

**Sources (web):** [Wikipedia — software architecture styles](https://en.wikipedia.org/wiki/List_of_software_architecture_styles_and_patterns), [Refactoring Guru — design patterns catalog](https://refactoring.guru/design-patterns/catalog), classic [GoF pattern catalog](http://www.cs.unc.edu/~stotts/GOF/hires/patcat.htm), [Kotlin collection operations](https://kotlinlang.org/docs/collection-operations.html). Algorithm families align with common DSA pattern guides (e.g. [sliding window archetypes](https://dsa.handbook.academy/patterns/sliding-window-archetypes/), [algorithmic pattern tables](https://vietbui.mintlify.app/patterns/algorithmic)).

---

## 1. Engineering principles & acronyms

| Name | Short description |
|------|-------------------|
| **SOLID — S** (Single Responsibility) | One reason to change per module/class; split mixed concerns. |
| **SOLID — O** (Open/Closed) | Open for extension (new types, strategies), closed for modification of stable core. |
| **SOLID — L** (Liskov Substitution) | Subtypes must honor contracts of supertypes; no surprising overrides. |
| **SOLID — I** (Interface Segregation) | Small, focused interfaces; clients depend only on methods they use. |
| **SOLID — D** (Dependency Inversion) | Depend on abstractions; high-level policy does not depend on low-level details. |
| **DRY** (Don’t Repeat Yourself) | One authoritative place for each piece of knowledge or behavior. |
| **KISS** (Keep It Simple, Stupid) | Prefer the simplest design that meets requirements; avoid cleverness. |
| **YAGNI** (You Aren’t Gonna Need It) | Do not build capability until it is required. |
| **SSOT** (Single Source of Truth) | One canonical definition per fact (config, rules, docs). |
| **Separation of concerns** | Divide system by distinct responsibilities with clear boundaries. |
| **LoD** (Law of Demeter) | Talk to immediate collaborators only; avoid long chains through strangers. |
| **Composition over inheritance** | Prefer composing behaviors over deep subclass trees. |
| **Fail fast** | Detect invalid state early (`require`, validation) instead of silent corruption. |
| **Design by contract** | Preconditions, postconditions, invariants document expected behavior. |
| **Least privilege** | Expose minimal API surface (`internal`, narrow ports). |
| **Boy Scout Rule** | Leave code cleaner than you found it; small incremental improvements. |
| **BDUF** (avoid) | Big Design Up Front — prefer incremental design with feedback. |

---

## 2. GRASP (object-oriented responsibility)

| Name | Short description |
|------|-------------------|
| **Information Expert** | Assign responsibility to the class that has the data needed to fulfill it. |
| **Creator** | Class A creates instances of B when A contains/aggregates/records/uses B closely. |
| **Controller** | Non-UI object handles system events for a use case (presentation layer). |
| **Low Coupling** | Reduce dependencies between types so changes stay local. |
| **High Cohesion** | Keep related behavior together in one module/class. |
| **Polymorphism** | Use interchangeable subtypes instead of branching on type codes. |
| **Pure Fabrication** | Introduce a helper class when no domain object fits a responsibility. |
| **Indirection** | Introduce a mediator to decouple two parties (e.g. port interface). |
| **Protected Variations** | Shield stable code from variation behind interfaces or adapters. |

---

## 3. Architectural styles & system patterns

Coarse-grained **how the system is organized**. See [Wikipedia list](https://en.wikipedia.org/wiki/List_of_software_architecture_styles_and_patterns).

| Name | Short description |
|------|-------------------|
| **Layered (n-tier)** | Presentation → business → data; dependencies point downward. |
| **Clean Architecture** | Domain at center; outer rings are UI, app, infrastructure. |
| **Hexagonal (Ports & Adapters)** | Domain core with ports; adapters implement I/O at the rim. |
| **Onion Architecture** | Same idea as clean/hexagonal; dependencies inward only. |
| **Modular monolith** | Single deployable unit with strict internal module boundaries. |
| **Monolithic** | One codebase, one deployment; simple ops, scales with team discipline. |
| **Microservices** | Many small deployable services; independent scale/deploy, distributed complexity. |
| **Service-oriented (SOA)** | Coarse services with shared contracts; often ESB-era enterprise style. |
| **Event-driven architecture (EDA)** | Components communicate via events through brokers; loose coupling. |
| **Message-driven** | Async messages between producers and consumers (queues, topics). |
| **CQRS** | Separate models/paths for commands (writes) and queries (reads). |
| **Event sourcing** | State stored as append-only events; current state is projection. |
| **Client–server** | Clients request; servers provide resources or processing. |
| **Pipes and filters** | Data flows through a chain of processing stages (stream/batch). |
| **Microkernel (plug-in)** | Minimal core + plug-in extensions (e.g. IDE, browser engines). |
| **Space-based** | Processing units share a virtual “space” for tuples/events (scale-out). |
| **Serverless (FaaS)** | Functions triggered by events; provider manages runtime scale. |
| **Strangler fig** | Gradually replace legacy by routing traffic to new subsystems. |
| **Circuit breaker** | Stop calling failing dependency; fail fast and recover later. |
| **Bulkhead** | Isolate resources so one failure does not sink the whole system. |
| **API gateway** | Single entry for clients routing to internal services. |
| **Backend for frontend (BFF)** | API tailored per client type (mobile vs web). |
| **Static content hosting** | Assets served from CDN/object storage off the app server. |
| **Sharding** | Partition data by key across nodes for scale. |
| **Throttling / rate limiting** | Cap request rate to protect capacity (see energy doc for mobile). |

### Presentation architecture (UI)

| Name | Short description |
|------|-------------------|
| **MVC** | Model, View, Controller — classic separation of UI and logic. |
| **MVP** | View passive; Presenter drives UI and talks to model. |
| **MVVM** | View binds to ViewModel state; common on Android/Compose. |
| **MVI** | Unidirectional cycle: Intent → Model → View (immutable state). |
| **UDF** (Unidirectional data flow) | State down, events up — our default ([ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md)). |

### Mobile (this project)

| Name | Short description |
|------|-------------------|
| **Single-module Android** | Feature code under `app/src/main`; optional `ui` / `presentation` / `domain` / `data` packages. |
| **Repository at rim** | Network/DB in `data`; interface in `domain`. |
| **Platform adapters** | Android system APIs behind narrow ports in `data` or `platform` package. |

---

## 4. Gang of Four (GoF) design patterns — 23

Canonical list: [Refactoring Guru catalog](https://refactoring.guru/design-patterns/catalog), [GoF catalog](http://www.cs.unc.edu/~stotts/GOF/hires/patcat.htm).

### Creational (object creation)

| Name | Short description |
|------|-------------------|
| **Abstract Factory** | Create families of related products without naming concrete classes. |
| **Builder** | Step-by-step construction of complex objects; same process, different results. |
| **Factory Method** | Subclasses decide which concrete product to instantiate. |
| **Prototype** | Clone existing instances instead of `new` on concrete types. |
| **Singleton** | One instance with global access — use sparingly; prefer DI scope. |

### Structural (composition)

| Name | Short description |
|------|-------------------|
| **Adapter** | Convert one interface into another clients expect. |
| **Bridge** | Split abstraction from implementation so both vary independently. |
| **Composite** | Tree of part-whole objects; treat leaf and branch uniformly. |
| **Decorator** | Add behavior by wrapping; alternative to subclass explosion. |
| **Facade** | Simple API over a complex subsystem. |
| **Flyweight** | Share intrinsic state across many fine-grained objects. |
| **Proxy** | Stand-in controlling access, lazy load, or remote calls. |

### Behavioral (interaction & algorithms)

| Name | Short description |
|------|-------------------|
| **Chain of Responsibility** | Pass request along a chain until something handles it. |
| **Command** | Encapsulate request as object; queue, undo, log. |
| **Interpreter** | Grammar for a mini-language; evaluate sentences. |
| **Iterator** | Sequential access without exposing collection internals. |
| **Mediator** | Central hub so objects do not reference each other directly. |
| **Memento** | Snapshot object state for restore (undo). |
| **Observer** | Subscribers notified when subject changes (→ Flow, callbacks). |
| **State** | Object behavior changes with internal state class. |
| **Strategy** | Interchangeable algorithms behind one interface. |
| **Template Method** | Skeleton algorithm in base class; subclasses fill steps. |
| **Visitor** | Add operations on object structure without changing element classes. |

---

## 5. Other common design & integration patterns

| Name | Short description |
|------|-------------------|
| **Repository** | Collection-like API over persistence; hides storage details. |
| **Unit of Work** | Track changes and commit one transactional batch. |
| **Data Mapper** | Translate between DB/DTO records and domain models. |
| **DTO** | Data transfer object for wire/API shapes only. |
| **Active Record** | Object carries persistence methods — convenient, couples domain to DB. |
| **Service Layer** | Application services orchestrate domain + repositories. |
| **Domain Model** | Rich domain objects with behavior, not anemic DTOs only. |
| **Anti-Corruption Layer** | Translate foreign model at boundary so domain stays clean. |
| **Gateway** | Encapsulate access to external system (payment, OCR API). |
| **Saga** | Distributed transaction as sequence of local steps + compensations. |
| **Outbox** | Write events to DB outbox; reliable publish to message bus. |
| **Retry with backoff** | Transient failures: wait and retry with increasing delay. |
| **Idempotency** | Same request twice has same effect as once. |
| **Cache-aside** | App reads cache first; on miss loads store and populates cache. |
| **Object pool** | Reuse expensive objects (connections, buffers) instead of allocate/free. |
| **Specification** | Composable predicates for business rules (AND/OR/NOT). |
| **Null Object** | No-op implementation instead of null checks everywhere. |

---

## 6. Algorithmic & data-structure patterns

Recognition tables are widely used in interview/DSA literature; see [algorithmic patterns overview](https://vietbui.mintlify.app/patterns/algorithmic) and [sliding window archetypes](https://dsa.handbook.academy/patterns/sliding-window-archetypes/).

| Name | Short description |
|------|-------------------|
| **Brute force** | Try all possibilities; baseline when n is small. |
| **Sliding window** | Contiguous subarray/substring; expand/shrink two bounds. |
| **Two pointers** | Two indices on array/string (often sorted); converge or chase. |
| **Fast & slow pointers** | Detect cycles or find middle of linked structure. |
| **Prefix sum** | Precompute cumulative sums for O(1) range queries. |
| **Difference array** | Range updates via prefix technique on deltas. |
| **Binary search** | Halve search space on sorted or monotonic predicate. |
| **Binary search on answer** | Search minimum feasible value in answer space. |
| **Divide and conquer** | Split problem, solve subproblems, merge results. |
| **Merge intervals** | Sort by start; sweep to merge overlapping ranges. |
| **Sweep line** | Process events in sorted order (scheduling, geometry). |
| **Greedy** | Local optimal choice; needs proof it is globally optimal. |
| **Dynamic programming** | Overlapping subproblems + optimal substructure; table or memo. |
| **Backtracking** | Explore choices with undo; all combinations/permutations/paths. |
| **BFS** | Level-order shortest path in unweighted graphs/grids. |
| **DFS** | Deep exploration; connectivity, cycles, topological prep. |
| **Topological sort** | Linear order respecting directed acyclic dependencies. |
| **Dijkstra / A\*** | Shortest path with non-negative / heuristic weights. |
| **Union–Find (DSU)** | Dynamic connectivity, detect cycles in undirected graphs. |
| **Minimum spanning tree** | Connect all nodes minimum total edge weight (Kruskal/Prim). |
| **Heap / priority queue** | Top-K, merge K sorted, scheduling by priority. |
| **Trie** | Prefix tree for strings, autocomplete, dictionary. |
| **Hash map counting** | Frequency, anagrams, two-sum style lookups O(1). |
| **Monotonic stack** | Next greater/smaller element, histogram problems. |
| **Monotonic queue** | Sliding window min/max in amortized O(n). |
| **Bitmask DP** | Subsets encoded as bits; small n exhaustive state. |
| **Bit manipulation** | XOR tricks, subsets, flags in compact integers. |
| **Memoization** | Cache recursive subproblem results. |
| **Tabulation** | Bottom-up DP filling a table. |
| **Recursion + pruning** | Cut search early when bounds prove suboptimal. |
| **Reservoir sampling** | Uniform random sample from stream of unknown length. |
| **Fisher–Yates shuffle** | Unbiased permutation in O(n). |
| **Kadane’s algorithm** | Maximum subarray sum in O(n). |
| **Floyd–Warshall** | All-pairs shortest paths (dense graphs). |
| **Bellman–Ford** | Shortest paths with negative edges (limited). |

| Name | Short description |
|------|-------------------|
| **Extension function** | Add behavior without subclassing (`fun List<T>.…`). |
| **Sealed hierarchy** | Closed set of variants (`sealed interface` events/results). |
| **Value class** | Inline wrapper for type safety (`@JvmInline value class`). |
| **Delegation** | `by` delegate for composition (`lazy`, custom). |
| **Scope functions** | `let`, `run`, `apply`, `also`, `with` for fluent blocks. |
| **Collection pipeline** | `map`/`filter`/`fold` instead of manual loops when clearer. |
| **Sequence** | Lazy chained ops for large or multi-step transforms. |
| **Result / sealed error** | Explicit success vs failure without exceptions only. |

Detail and “loops vs collections” rules: [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md) §4.

---

## 9. Testing & quality patterns

| Name | Short description |
|------|-------------------|
| **Arrange–Act–Assert (AAA)** | Setup, execute, verify in tests. |
| **Test double** | Fake, stub, mock, spy — replace collaborators in tests. |
| **Fixture** | Fixed sample data (fixture files in `test/`). |
| **Golden file** | Expected output stored in repo; diff on regression. |
| **Property-based testing** | Generate inputs; assert invariants (optional, Kotlin test libs). |
| **Smoke test** | Minimal path proves system runs (device run doc). |

---

## 10. How to use this during refactors

1. Read **[REFACTOR_GUIDE.md](./REFACTOR_GUIDE.md)** and complete Phases 0–7 for each batch.
2. Pick pattern **names** from this doc in PR descriptions and [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md).
3. Map to **this app** via [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md) §2–3; do not import patterns the product does not need (e.g. microservices).
4. Verify with Gradle tasks in playbook §6.

---

## Further reading (external)

| Topic | URL |
|-------|-----|
| Architecture styles list | https://en.wikipedia.org/wiki/List_of_software_architecture_styles_and_patterns |
| GoF pattern catalog (UNC) | http://www.cs.unc.edu/~stotts/GOF/hires/patcat.htm |
| Refactoring Guru (all GoF + more) | https://refactoring.guru/design-patterns/catalog |
| Clean Architecture (blog) | https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html |
| Kotlin collections | https://kotlinlang.org/docs/collection-operations.html |
| Kotlin coroutines | https://kotlinlang.org/docs/coroutines-guide.html |
| Android app architecture | https://developer.android.com/topic/architecture |

