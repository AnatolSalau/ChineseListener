# Strict refactor guide (PATTERNS_REFERENCE-driven)

> **Docs bundle:** Part of `docs/` loaded together when you use `@docs/WHAT_WE_DO.md`. Entry: [WHAT_WE_DO.md](./WHAT_WE_DO.md).

Mandatory operational manual for every refactor batch in ChineseListener. Pattern **definitions** live in [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md) (cite as **PR-§*n***). **Verification commands** live in [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md). **Per-file status** lives in [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md).

**Run Phases 0–7 in order** for structural or cross-file style refactors.

---

## Document hierarchy

| Doc | Role |
|-----|------|
| [PATTERNS_REFERENCE.md](./PATTERNS_REFERENCE.md) | Names and short descriptions (catalog) |
| **REFACTOR_GUIDE.md** (this file) | How to apply principles strictly |
| [REFACTOR_PLAYBOOK.md](./REFACTOR_PLAYBOOK.md) | Kotlin collection matrix, Gradle verify |
| [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md) | Living inventory + per-batch checklist |
| [ANDROID_PRACTICES.md](./ANDROID_PRACTICES.md) | Non-negotiable layers and UDF |

---

## In scope vs out of scope (YAGNI / KISS)

**In scope** (PR-§3): **modular monolith** (single `:app` module), **layered** / **clean** / **hexagonal (ports & adapters)**, **UDF** / **MVVM-shaped** presentation, **repository** + **DTO mapper** at rim, **throttling** on queues/workers ([ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md)).

**Catalog only — do not introduce without explicit approval:** microservices, SOA, CQRS, event sourcing, serverless, sharding, API gateway, BFF, space-based, strangler (no legacy system to strangle).

---

## Phase 0 — Before code (every batch)

**Refs:** PR-§1-KISS, PR-§1-YAGNI, PR-§1-BoyScout, PR-§1-BDUF (avoid).

1. Load `@docs/WHAT_WE_DO.md` bundle (Read tool for any missing file).
2. Open [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md); pick the **next open** row or define a new batch.
3. In chat or PR description, state: **Problem**, **hypothesis**, **approach**, **files/packages**, **risks**, **verification** plan.
4. Keep batch **small** (Boy Scout): one audit section or one layer at a time unless user approved a larger program.
5. Multi-file or new dependency: wait for **go ahead** per [chinese-listener-core.mdc](../.cursor/rules/chinese-listener-core.mdc).

**Exit criteria:** Written plan exists; scope is KISS/YAGNI-aligned.

---

## Phase 1 — Engineering principles (PR-§1)

Complete the checklist for the batch. Mark **N/A** only with one-line justification.

| ID | Principle | How to verify | Example violation | Fix |
|----|-----------|---------------|-------------------|-----|
| PR-§1-S | Single Responsibility | One reason to change per type | Parsing + HTTP in ViewModel | Move to domain / data |
| PR-§1-O | Open/Closed | Extend via new types/adapters | `when(provider)` in domain | New port impl in data |
| PR-§1-L | Liskov Substitution | Fakes honor port contracts | Fake throws unlike production | Align fake with port |
| PR-§1-I | Interface Segregation | Narrow ports | God-port with many methods | Split ports |
| PR-§1-D | Dependency Inversion | High-level depends on abstractions | Use case imports OkHttp | Interface in domain |
| PR-§1-DRY | DRY | One SSOT per rule | Duplicated validation in UI | Domain validator only |
| PR-§1-KISS | KISS | Simplest correct design | Over-abstracted one-liner | Inline or simplify |
| PR-§1-YAGNI | YAGNI | No speculative features | Unused feature flags | Remove or gate |
| PR-§1-SSOT | SSOT | One canonical place | Sort rules in UI and domain | Use case only |
| PR-§1-SoC | Separation of concerns | ui / presentation / domain / data | Composable calls API | ViewModel + repo |
| PR-§1-LoD | Law of Demeter | No deep chains | `vm.repo.client...` in UI | Events + state only |
| PR-§1-Comp | Composition over inheritance | Delegation, ports | Deep subclass tree | Compose behaviors |
| PR-§1-FF | Fail fast | `require` / early return | Silent bad input | Validate in domain |
| PR-§1-LP | Least privilege | `internal` where possible | Public internals | Narrow visibility |

### Stop-ship (do not merge)

- **`android.*` or Compose** in `domain`.
- **Business logic** in composables (beyond formatting/display).
- **`GlobalScope`** or unscoped coroutines for product work.
- **Behavior change** without test updates or explicit approval.

---

## Phase 2 — GRASP (PR-§2)

Every **new** type must map to **at least one** GRASP role, or merge into an existing type.

| GRASP | Typical assignment |
|-------|-------------------|
| **Controller** | ViewModel / presenter — handles events, no raw I/O |
| **Information Expert** | Use cases owning domain data/rules |
| **Creator** | Composition root builds graphs |
| **Low Coupling** | UI does not import data implementations |
| **High Cohesion** | Related domain types together |
| **Polymorphism** | Port implementations, sealed results |
| **Pure Fabrication** | Mappers, formatters — not domain entities |
| **Indirection** | Ports between presentation and data |
| **Protected Variations** | Adapters hide platform/API changes |

**Exit criteria:** New classes documented with GRASP role; no orphan types.

---

## Phase 3 — Architecture (PR-§3)

Top-down pass **before** collection-style edits.

### 3.1 Module

| Check | Expected |
|-------|----------|
| `:app` | Activity, manifest, DI root, theme; feature code in layer packages |

### 3.2 Layers

Dependency direction: `ui` → `presentation` → `domain` ← `data`.

### 3.3 Hexagonal checklist

- [ ] External I/O behind repository or gateway ports.
- [ ] No domain → `android.*`.
- [ ] UDF: `StateFlow` + sealed events; screens use callbacks only.

### 3.4 Resilience (mobile-appropriate)

| Pattern | Application |
|---------|-------------|
| **Throttle** | Queue depth, lifecycle ([ENERGY_EFFICIENCY.md](./ENERGY_EFFICIENCY.md)) |
| **Fail fast** | Permission / validation errors surfaced in UI state |
| **Cache-aside** | Session caches in data layer with domain policy |

**Exit criteria:** No layer violations in touched files.

---

## Phase 4 — GoF & integration patterns (PR-§4, PR-§5)

Tag **≥1 pattern name** in PR/chat when changing structure.

**Encouraged:** Strategy, Facade, Factory, Adapter, Repository, Data Mapper, Command (events), Observer (`StateFlow`), Anti-Corruption Layer (mappers).

**Use with care:** Singleton (document lifecycle), Decorator, Proxy.

**Not by default:** microservice patterns, full CQRS/event sourcing.

**Exit criteria:** Pattern tags recorded; no unapproved architectural style.

---

## Phase 5 — Algorithms (PR-§6)

When editing logic, preserve algorithm intent or update tests.

- Changing **scoring, merge, or validation** rules → update unit tests.
- Refactor loops to collections per [REFACTOR_PLAYBOOK.md §4](./REFACTOR_PLAYBOOK.md) and [chinese-listener-kotlin-collections.mdc](../.cursor/rules/chinese-listener-kotlin-collections.mdc).

**Exit criteria:** Tests green; algorithm pattern noted in audit batch checklist.

---

## Phase 6 — Concurrency, Kotlin, testing (PR-§7–9)

### Concurrency

- [ ] **Structured concurrency:** `viewModelScope` / lifecycle scopes.
- [ ] **Immutable state:** `copy` / `StateFlow.update`.
- [ ] No **GlobalScope**.

### Kotlin

Apply [REFACTOR_PLAYBOOK.md §4](./REFACTOR_PLAYBOOK.md) in **all** Kotlin sources:

- **No** imperative `for` / `while` / `do-while`.
- **Sealed** events/results; **data class** state.

### Testing

- [ ] **AAA** in new/changed tests.
- [ ] Domain/presentation: `app/src/test`.
- [ ] Instrumented tests when Android contracts change.

**Exit criteria:** Collection rules applied; tests added or updated.

---

## Phase 7 — Close batch

1. Run verification ([REFACTOR_PLAYBOOK.md §6](./REFACTOR_PLAYBOOK.md)):

```bash
./gradlew :app:testDebugUnitTest :app:assembleDebug
```

2. Device smoke per [RUN_ON_PHYSICAL_DEVICE.md](./RUN_ON_PHYSICAL_DEVICE.md) when permissions or background behavior changed.
3. Fill **Principle checklist (per batch)** in [REFACTOR_AUDIT.md](./REFACTOR_AUDIT.md).
4. Mark audit rows **done**; list **pattern tags** and **PR-§1** items verified.
